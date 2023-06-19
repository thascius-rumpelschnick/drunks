package org.kappa.client.component;

import org.kappa.client.utils.Direction;


public class DirectionComponent implements Component {

  private Direction direction;


  public DirectionComponent(final Direction direction) {
    this.direction = direction;
  }


  public Direction getDirection() {
    return this.direction;
  }


  public void setDirection(final Direction direction) {
    this.direction = direction;
  }

}
