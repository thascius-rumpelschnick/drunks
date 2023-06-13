package org.kappa.client.entity;

import org.kappa.client.component.Position;


public abstract class Node {

  protected Position position;
  protected int lives = -1;

  protected Node(final Position position, final int lives) {
    this.position = position;
    this.lives = lives;
  }

  public Position getPosition() {
    return this.position;
  }

  public int getLives() {
    return this.lives;
  }
}
