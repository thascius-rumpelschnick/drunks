package org.kappa.client.system;

import org.kappa.client.component.PositionComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EntityCreatedEvent;
import org.kappa.client.event.Listener;
import org.kappa.client.game.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NonPlayerEntitySystem implements UpdatableSystem, Listener<EntityCreatedEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(NonPlayerEntitySystem.class);

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
  public void update(final Time time) {
    // Lots of non player entity related business logic, i.e. vomit movents, cop movements / attacks, kebab rendering,

    for (final String entityId : this.nonPlayerEntityList) {
      LOGGER.debug("Entity: {} - Component: {}", entityId, this.entityManager.getComponent(entityId, PositionComponent.class));
    }
  }
}
