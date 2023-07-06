package org.kappa.client.system;

import org.kappa.client.ApplicationManager;
import org.kappa.client.component.*;
import org.kappa.client.entity.CopBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.*;
import org.kappa.client.game.Timer;
import org.kappa.client.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.kappa.client.event.EventType.ENTITY_CREATED;
import static org.kappa.client.event.EventType.ENTITY_REMOVED;
import static org.kappa.client.ui.UpdateGameType.*;


public class NonPlayerEntitySystem implements UpdatableSystem, Listener<EntityEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(NonPlayerEntitySystem.class);
  private static final Publisher PUBLISHER = EventPublisher.getInstance();

  private final Random random = new Random();
  private final EntityManager entityManager;
  private final SystemManager systemManager;
  private final Level level;
  private final List<String> nonPlayerEntityList;

  private int copCount;


  public NonPlayerEntitySystem(final EntityManager entityManager, final SystemManager systemManager, final Level level) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
    this.level = level;

    this.nonPlayerEntityList = new ArrayList<>();

    this.copCount = level.getLevelCops();
  }


  @Override
  public void updateOnEventReceived(final EntityEvent event) {
    Objects.requireNonNull(event);

    final var entityId = event.getBody();

    if (ENTITY_CREATED == event.getEventType()) {
      this.nonPlayerEntityList.add(entityId);

    } else if (ENTITY_REMOVED == event.getEventType()) {
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
    this.handleDynamicEntityCreation(timer);
    this.handleDynamicEntityMovement(timer);
    this.handleDynamicEntityAttack(timer);
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


  private void handleDynamicEntityCreation(final Timer timer) {
    final var entityId = IdHelper.createRandomUuid();

    if (
        timer.canAct()
            && this.copCount > 0
            && this.entityManager.filterEntityByComponentType(AttackComponent.class).size() < this.copCount
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
          .health(this.random.nextInt(1, this.level.getLevelCopsMaxHealth() + 1))
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

      PUBLISHER.publishEvent(new EntityEvent(entityId, ENTITY_CREATED));
    }
  }


  private void handleDynamicEntityMovement(final Timer timer) {
    final var canMove = timer.canAct();

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


  private void handleDynamicEntityAttack(final Timer timer) {
    final var canShoot = timer.canAct();

    if (canShoot) {
      final var attackers = this.entityManager.filterEntityByComponentType(AttackComponent.class);

      if (!attackers.isEmpty()) {
        final var attacker = attackers.get(this.random.nextInt(attackers.size()));

        PUBLISHER.publishEvent(new AttackEvent(attacker.getKey(), AttackType.CLUB));
      }

    }
  }


  private void removeEntity(final Map.Entry<String, Map<Class<? extends Component>, Component>> entityEntry) {
    final var entityId = entityEntry.getKey();

    PUBLISHER.publishEvent(new EntityEvent(entityId, ENTITY_REMOVED));
    this.removeEntity(entityId);

  }


  private void removeEntity(final String entityId) {
    final var applicationManager = ApplicationManager.getInstance();
    final var gameStats = applicationManager.getGameStats().orElseThrow();

    if (
        this.entityManager
            .filterEntityByComponentType(AttackComponent.class)
            .stream()
            .filter(e -> entityId.equals(e.getKey())).count() == 1
    ) {
      LOGGER.debug("CopCount: {}", this.copCount);

      this.copCount--;

      gameStats.setCopsCount(this.copCount);
      gameStats.incrementPlayerScore(1);

      var updateGameType = UPDATE_STATS;
      if (this.copCount <= 0) {
        updateGameType = LEVEL_COMPLETED;
      }

      applicationManager.fireGlobalEvent(updateGameType, gameStats, this);
    }

    if (applicationManager.getPlayer().orElseThrow().id().equals(entityId)) {
      applicationManager.fireGlobalEvent(GAME_OVER, gameStats, this);
    }

    this.entityManager.removeEntity(entityId);
    this.nonPlayerEntityList.remove(entityId);
  }
}
