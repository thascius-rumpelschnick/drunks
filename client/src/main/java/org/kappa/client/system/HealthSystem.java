package org.kappa.client.system;

import org.kappa.client.ApplicationManager;
import org.kappa.client.component.HealthComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.kappa.client.event.EventType.ENTITY_REMOVED;
import static org.kappa.client.ui.UpdateGameType.UPDATE_STATS;


public class HealthSystem implements System, Listener<DamageEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HealthSystem.class);

  private static final Publisher PUBLISHER = EventPublisher.getInstance();

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public HealthSystem(final EntityManager entityManager, final SystemManager systemManager) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  @Override
  public void updateOnEventReceived(final DamageEvent event) {
    Objects.requireNonNull(event);

    final var entityId = event.getEntity();
    final var damage = event.getBody();

    final var health = this.entityManager.getComponent(entityId, HealthComponent.class);

    if (health == null) {
      return;
    }

    health.applyDamage(damage);

    final var animationSystem = this.systemManager.getSystem(AnimationSystem.class);
    animationSystem.startDamageAnimation(entityId, health.getHealth(), health.hasBeenDestructed());

    if (health.hasBeenDestructed()) {
      PUBLISHER.publishEvent(new EntityEvent(entityId, ENTITY_REMOVED));
    }

    final var applicationManager = ApplicationManager.getInstance();

    if (applicationManager.getPlayer().orElseThrow().getId().equals(entityId)) {
      final var gameStats = applicationManager.getGameStats().orElseThrow();
      gameStats.setPlayerHealth(health.getHealth());

      applicationManager.fireGlobalEvent(UPDATE_STATS, gameStats, this);
    }
  }

}
