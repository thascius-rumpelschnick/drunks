package org.kappa.client.entity;


import org.kappa.client.component.PositionComponent;
import org.kappa.client.utils.Direction;

public class Player extends Node implements Movable {

  private final Direction direction = Direction.UP;

  public Player(final PositionComponent positionComponent, final int lives) {
    super(positionComponent, lives);
  }

  @Override
  public void move(final PositionComponent from, final PositionComponent to) {
    // ToDo: Set direction as well.
    this.positionComponent = to;
  }

  @Override
  public Direction getDirection() {
    return this.direction;
  }

}
