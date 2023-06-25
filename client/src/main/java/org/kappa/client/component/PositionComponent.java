package org.kappa.client.component;

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


  public void update(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

}
