package org.kappa.client.game;


import org.kappa.client.utils.Direction;

public class Player extends Node implements Movable {

  private final Direction direction = Direction.UP;

  public Player(final Coordinate position, final int lives) {
    super(position, lives);
  }

  @Override
  public void move(Coordinate from, Coordinate to) {
    // ToDo: Set direction as well.
    this.position = to;
  }

  @Override
  public Direction getDirection() {
    return this.direction;
  }

}
