package org.kappa.client.system;

import org.kappa.client.component.*;
import org.kappa.client.entity.CopBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.*;
import org.kappa.client.game.Timer;
import org.kappa.client.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class NonPlayerEntitySystem implements UpdatableSystem, Listener<EntityEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(NonPlayerEntitySystem.class);
  private static final Publisher PUBLISHER = EventPublisher.getInstance();

  private final EntityManager entityManager;
  private final SystemManager systemManager;
  private final Level level;

  private final Random random;
  private final List<String> nonPlayerEntityList;

  private int copCount;


  public NonPlayerEntitySystem(final EntityManager entityManager, final SystemManager systemManager, final Level level) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
    this.level = level;

    this.random = new Random();
    this.nonPlayerEntityList = new ArrayList<>();

    this.copCount = level.getLevelCops();
  }


  @Override
  public void updateOnEventReceived(final EntityEvent event) {
    Objects.requireNonNull(event);

    final var entityId = event.getBody();

    if (event instanceof EntityCreatedEvent) {
      this.nonPlayerEntityList.add(entityId);

    } else if (event instanceof EntityRemovedEvent) {
      this.removeEntity(entityId);

    } else {
      LOGGER.error("EntityEvent: {}", event);
      throw new IllegalArgumentException();
    }
  }


  @Override
  public void update(final Timer timer) {
    this.handleDamageLogic();
    this.handleDamageMovementLogic();
    this.handleDynamicEntityCreation(this.level, timer);
    this.handleDynamicEntityMovement(this.level, timer);
    this.handleDynamicEntityAttack(this.level, timer);
  }


  private void handleDamageLogic() {
    final var damageEntities = this.entityManager.filterEntityByComponentType(DamageComponent.class);

    damageEntities
        .stream()
        .filter(attacker -> this.nonPlayerEntityList.contains(attacker.getKey()))
        .forEach(attacker -> {
              final var entities = this.entityManager.filterEntityByComponent(attacker.getValue().get(PositionComponent.class));
              final var attacked = entities.filter(e -> !attacker.getKey().equals(e.getKey())).findFirst();

              // ToDo: Check if attacker.getValue().get(DamageComponent .class) != null?
              if (attacked.isPresent()) {
                try {
                  final var entityId = attacked.get().getKey();
                  final var damageComponent = (DamageComponent) attacker.getValue().get(DamageComponent.class);

                  this.removeEntity(attacker);

                  final var event = new DamageEvent(entityId, damageComponent.damage());
                  PUBLISHER.publishEvent(event);
                } catch (final Exception exception) {
                  LOGGER.error("HandleDamageLogic: {}", exception.getMessage(), exception);
                }
              }
            }
        );
  }


  private void handleDamageMovementLogic() {
    final var damageEntities = this.entityManager.filterEntityByComponentType(DamageComponent.class);

    damageEntities
        .stream()
        .filter(e -> this.nonPlayerEntityList.contains(e.getKey()) && e.getValue().get(VelocityComponent.class) != null)
        .forEach(e -> {
              final var entityId = e.getKey();
              final var movementSystem = this.systemManager.getSystem(MovementSystem.class);

              movementSystem.moveDamageEntity(entityId);
            }
        );
  }


  private void handleDynamicEntityCreation(final Level level, final Timer timer) {
    final var canBeCreated = this.copCount > 0 && this.canDo(timer);
    final var entityId = IdHelper.createRandomUuid();

    if (
        canBeCreated
            && this.entityManager.filterEntityByComponentType(AttackComponent.class).size() < level.getLevelCops()
            && this.nonPlayerEntityList.stream().noneMatch(entityId::equals)
    ) {
      final var collision = this.systemManager.getSystem(CollisionDetectionSystem.class);

      final var xAxisFields = LayoutValues.GAMEBOARD_WITH / LayoutValues.GAMEBOARD_TILE;
      var triedFields = 1;
      var hasCollision = false;
      final var y = 0;
      int x;

      do {
        x = this.random.nextInt(xAxisFields) * LayoutValues.GAMEBOARD_TILE;
        hasCollision = collision.detectCollision(x, y).findAny().isPresent();

        if (hasCollision) {
          triedFields++;
        }
      } while (hasCollision && (triedFields <= xAxisFields));

      if (hasCollision) {
        return;
      }

      final var direction = this.getRandomDirection();
      final var cop = CopBuilder.get()
          .id(entityId)
          .health(this.random.nextInt(1, level.getLevelCopsMaxHealth() + 1))
          .render(direction)
          .position(x, y)
          .direction(direction)
          .velocity(LayoutValues.GAMEBOARD_TILE)
          .movementAnimation()
          .damageAnimation()
          .attack()
          .build();

      this.entityManager.createEntity(cop.getId());
      this.entityManager.putComponent(cop.getId(), cop.getHealthComponent());
      this.entityManager.putComponent(cop.getId(), cop.getDirectionComponent());
      this.entityManager.putComponent(cop.getId(), cop.getRenderComponent());
      this.entityManager.putComponent(cop.getId(), cop.getPositionComponent());
      this.entityManager.putComponent(cop.getId(), cop.getVelocityComponent());
      this.entityManager.putComponent(cop.getId(), cop.getMovementAnimationComponent());
      this.entityManager.putComponent(cop.getId(), cop.getDamageAnimationComponent());
      this.entityManager.putComponent(cop.getId(), cop.getAttackComponent());

      this.nonPlayerEntityList.add(cop.getId());

      PUBLISHER.publishEvent(new EntityCreatedEvent(entityId));
    }
  }


  private void handleDynamicEntityMovement(final Level level, final Timer timer) {
    final var canMove = this.canDo(timer);

    if (canMove) {
      final var movers = this.entityManager.filterEntityByComponentType(AttackComponent.class);

      if (!movers.isEmpty()) {
        final var mover = movers.get(this.random.nextInt(movers.size()));
        Direction direction = this.random.nextBoolean()
            ? ((DirectionComponent) mover.getValue().get(DirectionComponent.class)).getDirection()
            : this.getRandomDirection();

        while (this.isOccupiedByOtherDynamicEntity(
            (PositionComponent) mover.getValue().get(PositionComponent.class),
            (VelocityComponent) mover.getValue().get(VelocityComponent.class),
            direction
        )) {
          direction = this.getRandomDirection();
        }

        PUBLISHER.publishEvent(new MovementEvent(mover.getKey(), direction));
      }
    }
  }


  private boolean isOccupiedByOtherDynamicEntity(final PositionComponent position, final VelocityComponent velocity, final Direction direction) {
    final var collision = this.systemManager.getSystem(CollisionDetectionSystem.class);
    final var targetedPosition = MovementSystem.computeNewPosition(position, velocity, direction);

    return collision.detectCollision(targetedPosition).anyMatch(e -> e.getValue().get(VelocityComponent.class) != null);
  }


  private Direction getRandomDirection() {
    final var directionValues = Direction.values();

    return Direction.valueOf(directionValues[this.random.nextInt(directionValues.length)].name());
  }


  private void handleDynamicEntityAttack(final Level level, final Timer timer) {
    final var canShoot = this.canDo(timer);

    if (canShoot) {
      final var attackers = this.entityManager.filterEntityByComponentType(AttackComponent.class);

      if (!attackers.isEmpty()) {
        final var attacker = attackers.get(this.random.nextInt(attackers.size()));

        PUBLISHER.publishEvent(new AttackEvent(attacker.getKey(), AttackType.CLUB));
      }

    }
  }


  private boolean canDo(final Timer timer) {
    // return this.random.nextBoolean() && this.random.nextInt(1, 1 + 1) == timer.getRound();
    return this.random.nextBoolean() && this.random.nextBoolean();
  }


  private void removeEntity(final Map.Entry<String, Map<Class<? extends Component>, Component>> entityEntry) {
    final var entityId = entityEntry.getKey();

    PUBLISHER.publishEvent(new EntityRemovedEvent(entityId));
    this.removeEntity(entityId);

  }


  private void removeEntity(final String entityId) {
    if (this.entityManager.filterEntityByComponentType(AttackComponent.class).stream().filter(e -> entityId.equals(e.getKey())).count() == 1) {
      this.copCount--;
      LOGGER.debug("CopCount: {}", this.copCount);
    }

    this.entityManager.removeEntity(entityId);
    this.nonPlayerEntityList.remove(entityId);
  }
}
