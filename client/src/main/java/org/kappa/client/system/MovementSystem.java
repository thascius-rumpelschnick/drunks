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
    final var sprite = this.entityManager.getComponent(event.getEntity(), RenderComponent.class);
    final var animation = this.entityManager.getComponent(event.getEntity(), MovementAnimationComponent.class);
    final var direction = this.entityManager.getComponent(event.getEntity(), DirectionComponent.class);
    final var position = this.entityManager.getComponent(event.getEntity(), PositionComponent.class);

    animation.animate(sprite.imageView(), event.getBody());

    // this.entityManager.putComponent(); ToDo: Complete!

  }


  @Override
  public void update() {

  }

  private boolean isOutOfBounds(final PositionComponent destination) {
    // System.out.println("x:" + destination.x() + ", y:" + destination.y());

    return destination.x() < 0
        || destination.x() >= LayoutValues.GAMEBOARD_WITH
        || destination.y() < 0
        || destination.y() >= LayoutValues.GAMEBOARD_HEIGHT;
  }

}
