package org.kappa.client.game;

import org.kappa.client.utils.Level;


public class GameStats {

  private final Level level;

  private final int copsCount;

  private final int playerHealth;

  private final int playerScore;


  public GameStats(final Level level, final int playerHealth, final int playerScore) {
    this.level = level;
    this.copsCount = level.getLevelCops();
    this.playerHealth = playerHealth;
    this.playerScore = playerScore;
  }


  public Level getLevel() {
    return this.level;
  }


  public int getCopsCount() {
    return this.copsCount;
  }


  public int getPlayerHealth() {
    return this.playerHealth;
  }


  public int getPlayerScore() {
    return this.playerScore;
  }
}
