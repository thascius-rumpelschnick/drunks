package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.component.VelocityComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EntityEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.Listener;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.utils.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.kappa.client.event.EventType.ENTITY_REMOVED;


public class MovementSystem implements System, Listener<MovementEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MovementSystem.class);
  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();

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

      final var newPosition = computeNewPosition(position, velocity, movementDirection);

      final var collisionDetectionSystem = this.systemManager.getSystem(CollisionDetectionSystem.class);

      if (
          collisionDetectionSystem.isOutOfBounds(newPosition)
              || collisionDetectionSystem.detectCollision(newPosition).anyMatch(e -> !entityId.equals(e.getKey())
          )
      ) {
        // LOGGER.debug("Out of bounds or collision detected: x = {}, y = {}", x, y);
        return;
      }

      sprite.update(newPosition);
      position.update(newPosition);
    }
  }


  public void moveDamageEntity(final String entityId) {
    final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var position = this.entityManager.getComponent(entityId, PositionComponent.class);
    final var velocity = this.entityManager.getComponent(entityId, VelocityComponent.class);
    final var direction = this.entityManager.getComponent(entityId, DirectionComponent.class);

    final var newPosition = computeNewPosition(position, velocity, direction.getDirection());

    final var collisionDetectionSystem = this.systemManager.getSystem(CollisionDetectionSystem.class);

    if (collisionDetectionSystem.isOutOfBounds(newPosition)) {
      PUBLISHER.publishEvent(new EntityEvent(entityId, ENTITY_REMOVED));
    }

    sprite.update(newPosition);
    position.update(newPosition);
  }


  public static PositionComponent computeNewPosition(
      final PositionComponent previousPosition,
      final VelocityComponent velocity,
      final Direction movementDirection
  ) {
    var x = previousPosition.x();
    var y = previousPosition.y();

    switch (movementDirection) {
      case UP -> y -= velocity.velocity();
      case DOWN -> y += velocity.velocity();
      case LEFT -> x -= velocity.velocity();
      case RIGHT -> x += velocity.velocity();
      default -> LOGGER.error("PLAYER: WHOOT?");
    }

    return new PositionComponent(x, y);
  }

}
