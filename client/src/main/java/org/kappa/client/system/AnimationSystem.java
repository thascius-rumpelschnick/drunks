package org.kappa.client.system;

import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.utils.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AnimationSystem implements System {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnimationSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public AnimationSystem(final EntityManager entityManager, final SystemManager systemManager) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }

  public void startMovementAnimation(final String entityId, final Direction movementDirection) {
    final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var animation = this.entityManager.getComponent(entityId, MovementAnimationComponent.class);

    animation.animate(sprite.imageView(), movementDirection);
  }


  @Override
  public void update() {

  }
}