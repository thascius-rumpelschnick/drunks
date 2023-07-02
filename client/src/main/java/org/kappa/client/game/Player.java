package org.kappa.client.game;

public class Player {

  private final String id;
  private final String username;
  private final String password;
  private final int highScore;
  private final int score;
  private final int health;


  public Player(final String id, final String username, final String password, final int highScore, final int score, final int health) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.highScore = highScore;
    this.score = score;
    this.health = health;
  }


  public String getPassword() {
    return this.password;
  }


  public int getHighScore() {
    return this.highScore;
  }


  public int getScore() {
    return this.score;
  }


  public int getHealth() {
    return this.health;
  }


  public String getId() {
    return this.id;
  }


  public String getUsername() {
    return this.username;
  }

}
