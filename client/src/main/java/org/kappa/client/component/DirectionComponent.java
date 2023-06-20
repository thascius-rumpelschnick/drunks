package org.kappa.client.component;

import org.kappa.client.utils.Direction;


public class DirectionComponent implements Component {

  private Direction direction;


  public DirectionComponent(final Direction direction) {
    this.direction = direction;
  }


  public Direction direction() {
    return this.direction;
  }


  public void update(final Direction direction) {
    this.direction = direction;
  }

}
