package org.kappa.client.game;

public abstract class Node {

  protected Coordinate position;
  protected int lives = -1;

  protected Node(final Coordinate position, final int lives) {
    this.position = position;
    this.lives = lives;
  }

  public Coordinate getPosition() {
    return this.position;
  }

  public int getLives() {
    return this.lives;
  }
}
