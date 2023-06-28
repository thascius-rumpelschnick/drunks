package org.kappa.client.system;

import org.kappa.client.component.Component;
import org.kappa.client.component.DamageComponent;
import org.kappa.client.component.HealthComponent;
import org.kappa.client.component.PositionComponent;
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
    // Lots of non player entity related business logic, i.e. vomit movents, cop movements / attacks, kebab rendering,

    //    for (final String entityId : this.nonPlayerEntityList) {
    //      LOGGER.debug("Entity: {} - Component: {}", entityId, this.entityManager.getComponent(entityId, PositionComponent.class));
    //    }

    final var damageEntities = this.entityManager.filterEntityByComponentType(DamageComponent.class);
    for (final Map.Entry<String, Map<Class<? extends Component>, Component>> attackingEntity : damageEntities) {
      if (this.nonPlayerEntityList.contains(attackingEntity.getKey())) {
        final var attackedEntity = this.entityManager.filterEntityByComponent(attackingEntity.getValue().get(PositionComponent.class));
        if (attackedEntity.isPresent() && attackedEntity.get().getValue().get(HealthComponent.class) != null) {
          final var entityId = attackedEntity.get().getKey();
          final var damageComponent = (DamageComponent) attackingEntity.getValue().get(DamageEvent.class);

          PUBLISHER.publishEvent(new DamageEvent(entityId, damageComponent.damage()));
          LOGGER.debug("Entity: {}", attackedEntity.get().getKey());
        }
      }
    }
  }

}
