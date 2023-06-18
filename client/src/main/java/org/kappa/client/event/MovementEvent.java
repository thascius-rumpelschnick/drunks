package org.kappa.client.event;

import org.kappa.client.utils.Direction;

import static org.kappa.client.event.EventType.MOVEMENT;


public class MovementEvent extends Event {

  private final Direction direction;


  public MovementEvent(final String entity, final Direction direction) {
    super(entity, MOVEMENT);
    this.direction = direction;
  }


  @Override
  public Direction getBody() {
    return this.direction;
  }

}
