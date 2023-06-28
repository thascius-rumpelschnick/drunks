package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.component.VelocityComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.Listener;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.game.Timer;
import org.kappa.client.utils.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class MovementSystem implements UpdatableSystem, Listener<MovementEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MovementSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public MovementSystem(final EntityManager entityManager, final SystemManager systemManager) {
    Objects.requireNonNull(entityManager);
    Objects.requireNonNull(systemManager);

    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  @Override
  public void updateOnEventReceived(final MovementEvent event) {
    final String entityId = event.getEntity();
    final Direction movementDirection = event.getBody();

    final var animationSystem = this.systemManager.getSystem(AnimationSystem.class);
    animationSystem.startMovementAnimation(entityId, movementDirection);

    this.move(entityId, movementDirection);
  }


  private void move(final String entityId, final Direction movementDirection) {
    final var direction = this.entityManager.getComponent(entityId, DirectionComponent.class);
    final var previousDirection = direction.getDirection();

    direction.update(movementDirection);

    if (movementDirection.compareTo(previousDirection) == 0) {
      final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
      final var position = this.entityManager.getComponent(entityId, PositionComponent.class);
      final var velocity = this.entityManager.getComponent(entityId, VelocityComponent.class);

      final var collisionDetectionSystem = this.systemManager.getSystem(CollisionDetectionSystem.class);

      var x = position.x();
      var y = position.y();

      switch (movementDirection) {
        case UP -> y -= velocity.velocity();
        case DOWN -> y += velocity.velocity();
        case LEFT -> x -= velocity.velocity();
        case RIGHT -> x += velocity.velocity();
        default -> LOGGER.error("PLAYER: WHOOT?");
      }

      if (collisionDetectionSystem.isOutOfBounds(x, y) || collisionDetectionSystem.detectCollision(x, y).isPresent()) {
        LOGGER.debug("Out of bounds or collision detected: x = {}, y = {}", x, y);
        return;
      }

      sprite.update(x, y);
      position.update(x, y);
    }
  }


  @Override
  public void update(final Timer timer) {

  }

}
