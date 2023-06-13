package org.kappa.client.entity;

import org.kappa.client.component.Position;
import org.kappa.client.utils.Direction;

public interface Movable {

  void move(final Position from, final Position to);

  Direction getDirection();

}
