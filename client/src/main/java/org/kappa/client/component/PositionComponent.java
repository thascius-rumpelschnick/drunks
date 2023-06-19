package org.kappa.client.component;

public class PositionComponent implements Component {

  private int x;
  private int y;


  public PositionComponent(final int x, final int y) {
    this.x = x;
    this.y = y;
  }


  public int getX() {
    return this.x;
  }


  public void setX(final int x) {
    this.x = x;
  }


  public int getY() {
    return this.y;
  }


  public void setY(final int y) {
    this.y = y;
  }
}
