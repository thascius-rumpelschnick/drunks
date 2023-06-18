package org.kappa.client.entity;

import org.kappa.client.component.PositionComponent;
import org.kappa.client.utils.Direction;

public interface Movable {

  void move(final PositionComponent from, final PositionComponent to);

  Direction getDirection();

}
