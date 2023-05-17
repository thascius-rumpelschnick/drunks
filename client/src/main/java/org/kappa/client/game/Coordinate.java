package org.kappa.client.game;

import java.util.Objects;

public record Coordinate(int x, int y) {

  public Coordinate {
    Objects.requireNonNull(x, "x must not be null.");
    Objects.requireNonNull(y, "y must not be null.");
  }

}
