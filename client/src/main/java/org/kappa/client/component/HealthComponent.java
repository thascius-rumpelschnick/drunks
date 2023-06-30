package org.kappa.client.component;

public class HealthComponent implements Component {
  private int health;


  public HealthComponent(final int health) {
    this.health = health;
  }


  public void applyDamage(final int damage) {
    this.health -= damage;
  }


  public int getHealth() {
    return this.health;
  }


  public boolean hasBeenDestructed() {
    return this.health <= 0;
  }

}
