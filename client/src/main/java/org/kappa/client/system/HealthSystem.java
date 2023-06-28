package org.kappa.client.system;

import org.kappa.client.component.HealthComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.DamageEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.Listener;
import org.kappa.client.event.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


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
    animationSystem.startDamageAnimation(entityId, health.getHealth(), health.hasBeeDistructed());

    if (health.hasBeeDistructed()) {
      this.removeEntity(entityId);
    }
  }


  private void removeEntity(final String entityId) {
    this.systemManager.getSystem(RenderSystem.class).removeEntityFromGameBoard(entityId);
    this.entityManager.removeEntity(entityId);
  }
}
