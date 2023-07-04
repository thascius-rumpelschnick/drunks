package org.kappa.client.game;

public class Player {

  private final String id;
  private final String username;
  private final String password;
  private final int highScore;


  public Player(final String id, final String username, final String password, final int highScore) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.highScore = highScore;
  }


  public String getPassword() {
    return this.password;
  }


  public int getHighScore() {
    return this.highScore;
  }


  public String getId() {
    return this.id;
  }


  public String getUsername() {
    return this.username;
  }

}
