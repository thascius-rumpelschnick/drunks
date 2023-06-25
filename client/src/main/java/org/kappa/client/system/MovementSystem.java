package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.Listener;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.utils.LayoutValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MovementSystem implements System, Listener<MovementEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MovementSystem.class);

  private final EntityManager entityManager;


  public MovementSystem(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public void updateOnEventReceived(final MovementEvent event) {
    final var entityId = event.getEntity();
    final var movementDirection = event.getBody();

    final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var animation = this.entityManager.getComponent(entityId, MovementAnimationComponent.class);
    final var direction = this.entityManager.getComponent(entityId, DirectionComponent.class);
    final var position = this.entityManager.getComponent(entityId, PositionComponent.class);

    animation.animate(sprite.imageView(), movementDirection);

    var x = position.x();
    var y = position.y();

    switch (movementDirection) {
      case UP -> y -= LayoutValues.GAMEBOARD_TILE;
      case DOWN -> y += LayoutValues.GAMEBOARD_TILE;
      case LEFT -> x -= LayoutValues.GAMEBOARD_TILE;
      case RIGHT -> x += LayoutValues.GAMEBOARD_TILE;
      default -> LOGGER.error("PLAYER: WHOOT?");
    }

    if (this.isOutOfBounds(x, y)) {
      LOGGER.debug("Out of bounds: x = {}, y = {}", x, y);
      return;
    }

    sprite.imageView().setX(x);
    sprite.imageView().setY(y);

    direction.update(movementDirection);
    position.update(x, y);
  }


  @Override
  public void update() {

  }


  private boolean isOutOfBounds(final int x, final int y) {
    return x < 0
        || x >= LayoutValues.GAMEBOARD_WITH
        || y < 0
        || y >= LayoutValues.GAMEBOARD_HEIGHT;
  }

}
