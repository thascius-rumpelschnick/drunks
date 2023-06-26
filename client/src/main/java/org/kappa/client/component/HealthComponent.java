package org.kappa.client.component;

public class HealthComponent implements Component {
  private final int maxHealth;
  private int currentHealth;


  public HealthComponent(final int maxHealth) {
    this.maxHealth = maxHealth;
    this.currentHealth = maxHealth;
  }


  public int getMaxHealth() {
    return this.maxHealth;
  }


  public int getCurrentHealth() {
    return this.currentHealth;
  }


  public void setCurrentHealth(final int currentHealth) {
    this.currentHealth = Math.max(0, Math.min(currentHealth, this.maxHealth));
  }


  public void damage(final int amount) {
    this.setCurrentHealth(this.currentHealth - amount);
  }


  public boolean isDead() {
    return this.currentHealth <= 0;
  }
}
