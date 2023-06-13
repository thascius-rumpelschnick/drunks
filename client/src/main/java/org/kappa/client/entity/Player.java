package org.kappa.client.entity;


import org.kappa.client.component.Position;
import org.kappa.client.utils.Direction;

public class Player extends Node implements Movable {

  private final Direction direction = Direction.UP;

  public Player(final Position position, final int lives) {
    super(position, lives);
  }

  @Override
  public void move(final Position from, final Position to) {
    // ToDo: Set direction as well.
    this.position = to;
  }

  @Override
  public Direction getDirection() {
    return this.direction;
  }

}
