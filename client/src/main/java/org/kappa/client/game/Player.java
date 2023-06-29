package org.kappa.client.game;

public class Player {

  private final String id;
  private final String name;
  private final int highScore;
  private int score;
  private int health;


  public Player(final String id, final String name, final int highScore) {
    this.id = id;
    this.name = name;
    this.highScore = highScore;
  }


  public String getId() {
    return this.id;
  }


  public String getName() {
    return this.name;
  }

}
