package org.kappa.client.game;

import org.kappa.client.utils.Direction;

public interface Movable {

  void move(final Coordinate from, final Coordinate to);

  Direction getDirection();

}
