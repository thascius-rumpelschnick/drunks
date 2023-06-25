package org.kappa.client.entity;

import org.kappa.client.component.PositionComponent;


public abstract class Node {

  protected PositionComponent positionComponent;
  protected int lives = -1;

  protected Node(final PositionComponent positionComponent, final int lives) {
    this.positionComponent = positionComponent;
    this.lives = lives;
  }

  public PositionComponent getPosition() {
    return this.positionComponent;
  }

  public int getLives() {
    return this.lives;
  }
}
