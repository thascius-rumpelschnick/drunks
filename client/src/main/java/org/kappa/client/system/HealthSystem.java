package org.kappa.client.system;

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

    LOGGER.debug("HealthSystem.updateOnEventReceived {}", event.getEntity());

    // event.getEntity() -> Id -> entityManager -> Health Component
    // systemManager.getSystem(AnimationSystem.class) -> animateDamage() -> entityManager -> RenderComponent + DamageAnimationComponen
    // event.getBody() -> Damage
  }
}
