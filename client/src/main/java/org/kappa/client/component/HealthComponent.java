package org.kappa.client.component;

public class HealthComponent implements Component {
  private int health;


  public HealthComponent(final int maxHealth) {
    this.health = health;
  }


  public int getMaxHealth() {
    return this.health;
  }


  public void setCurrentHealth(final int currentHealth) {
    this.health = currentHealth;
  }


  public void damage(final int amount) {
    this.setCurrentHealth(this.health - amount);
  }


  public boolean isDead() {
    return this.health <= 0;
  }
}
