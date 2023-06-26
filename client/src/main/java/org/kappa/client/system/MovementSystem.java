package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.Listener;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.LayoutValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MovementSystem implements System, Listener<MovementEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MovementSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public MovementSystem(final EntityManager entityManager, final SystemManager systemManager) {
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
    final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var direction = this.entityManager.getComponent(entityId, DirectionComponent.class);
    final var position = this.entityManager.getComponent(entityId, PositionComponent.class);

    final var collisionDetectionSystem = this.systemManager.getSystem(CollisionDetectionSystem.class);

    var x = position.x();
    var y = position.y();

    switch (movementDirection) {
      case UP -> y -= LayoutValues.GAMEBOARD_TILE;
      case DOWN -> y += LayoutValues.GAMEBOARD_TILE;
      case LEFT -> x -= LayoutValues.GAMEBOARD_TILE;
      case RIGHT -> x += LayoutValues.GAMEBOARD_TILE;
      default -> LOGGER.error("PLAYER: WHOOT?");
    }

    if (collisionDetectionSystem.isOutOfBounds(x, y) || collisionDetectionSystem.detectCollision(x, y).isPresent()) {
      LOGGER.debug("Out of bounds or collision detected: x = {}, y = {}", x, y);
      return;
    }

    sprite.update(x, y);
    direction.update(movementDirection);
    position.update(x, y);
  }


  @Override
  public void update() {

  }

}
