package org.kappa.client.game;

import org.kappa.client.utils.Level;

import static org.kappa.client.utils.Level.ONE;


public class GameStats {

  private Level level;

  private int copsCount;

  private int playerHealth;

  private int playerScore;


  public GameStats(final Level level, final int playerHealth, final int playerScore) {
    this.level = level;
    this.copsCount = level.getLevelCops();
    this.playerHealth = playerHealth;
    this.playerScore = playerScore;
  }


  public void nextLevel() {
    this.level = this.level.nextLevel().orElse(ONE);
  }


  public Level getLevel() {
    return this.level;
  }


  public int getCopsCount() {
    return this.copsCount;
  }


  public void setCopsCount(final int copsCount) {
    this.copsCount = copsCount;
  }


  public int getPlayerHealth() {
    return this.playerHealth;
  }


  public void setPlayerHealth(final int playerHealth) {
    this.playerHealth = playerHealth;
  }


  public int getPlayerScore() {
    return this.playerScore;
  }


  public void incrementPlayerScore(final int points) {
    this.playerScore += points;
  }
}
