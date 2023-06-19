package org.kappa.client.system;

import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.Listener;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.utils.LayoutValues;


public class MovementSystem implements System, Listener<MovementEvent> {

  private final EntityManager entityManager;


  public MovementSystem(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public void updateOnEventReceived(final MovementEvent event) {
    final var entityId = event.getEntity();
    final var newDirection = event.getBody();


    final var sprite = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var animation = this.entityManager.getComponent(entityId, MovementAnimationComponent.class);
    final var direction = this.entityManager.getComponent(entityId, DirectionComponent.class);
    final var position = this.entityManager.getComponent(entityId, PositionComponent.class);


    animation.animate(sprite.imageView(), newDirection);


  }


  @Override
  public void update() {

  }

  private boolean isOutOfBounds(final PositionComponent destination) {
    // System.out.println("x:" + destination.x() + ", y:" + destination.y());

    return destination.getX() < 0
        || destination.getX() >= LayoutValues.GAMEBOARD_WITH
        || destination.getY() < 0
        || destination.getY() >= LayoutValues.GAMEBOARD_HEIGHT;
  }

}
