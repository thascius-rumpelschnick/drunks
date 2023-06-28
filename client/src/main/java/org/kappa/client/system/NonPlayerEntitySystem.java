package org.kappa.client.system;

import org.kappa.client.component.*;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.*;
import org.kappa.client.game.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NonPlayerEntitySystem implements UpdatableSystem, Listener<EntityCreatedEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(NonPlayerEntitySystem.class);

  private static final Publisher PUBLISHER = EventPublisher.getInstance();

  private final EntityManager entityManager;
  private final SystemManager systemManager;
  private final List<String> nonPlayerEntityList;


  public NonPlayerEntitySystem(final EntityManager entityManager, final SystemManager systemManager) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
    this.nonPlayerEntityList = new ArrayList<>();
  }


  @Override
  public void updateOnEventReceived(final EntityCreatedEvent event) {
    Objects.requireNonNull(event);

    this.nonPlayerEntityList.add(event.getBody());
  }


  @Override
  public void update(final Timer timer) {
    // Lots of non player entity related business logic, i.e. vomit movents, cop movements / attacks, kebab rendering...

    this.handleDamageLogic();
    this.handleMovementLogic();
  }


  private void handleDamageLogic() {
    final var damageEntities = this.entityManager.filterEntityByComponentType(DamageComponent.class);

    damageEntities
        .stream()
        .filter(attacker -> this.nonPlayerEntityList.contains(attacker.getKey()))
        .forEach(attacker -> {
              final var attacked = this.entityManager.filterEntityByComponent(attacker.getValue().get(PositionComponent.class));

              if (attacked.isPresent() && attacked.get().getValue().get(HealthComponent.class) != null) {
                final var entityId = attacked.get().getKey();
                final var damageComponent = (DamageComponent) attacker.getValue().get(DamageComponent.class);

                PUBLISHER.publishEvent(new DamageEvent(entityId, damageComponent.damage()));

                this.removeEntity(attacker);
              }
            }
        );
  }


  private void handleMovementLogic() {
    final var damageEntities = this.entityManager.filterEntityByComponentType(DamageComponent.class);

    damageEntities
        .stream()
        .filter(attacker -> this.nonPlayerEntityList.contains(attacker.getKey()))
        .forEach(attacker -> {
              final var direction = (DirectionComponent) attacker.getValue().get(DirectionComponent.class);
              final var position = (PositionComponent) attacker.getValue().get(PositionComponent.class);

              final var collisionDetectionSystem = this.systemManager.getSystem(CollisionDetectionSystem.class);

            }
        );
  }


  private void removeEntity(final Map.Entry<String, Map<Class<? extends Component>, Component>> entityEntry) {
    this.entityManager.removeEntity(entityEntry.getKey());
    this.nonPlayerEntityList.remove(entityEntry.getKey());
  }
}
