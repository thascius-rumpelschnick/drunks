package org.kappa.client.component;

import java.util.Objects;


public class PositionComponent implements Component {

  private int x;
  private int y;


  public PositionComponent(final int x, final int y) {
    this.x = x;
    this.y = y;
  }


  public int x() {
    return this.x;
  }


  public int y() {
    return this.y;
  }


  public void update(final PositionComponent positionComponent) {
    this.update(positionComponent.x(), positionComponent.y());
  }


  public void update(final int x, final int y) {
    this.x = x;
    this.y = y;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof final PositionComponent that)) return false;

    return this.x == that.x && this.y == that.y;
  }


  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
