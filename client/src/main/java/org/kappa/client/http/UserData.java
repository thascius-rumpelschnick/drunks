package org.kappa.client.http;

import org.kappa.client.utils.Level;


public record UserData(String username, int highScore, Level level) {

  @Override
  public String toString() {
    return "UserData{" +
        "username='" + this.username + '\'' +
        ", highScore=" + this.highScore +
        ", level=" + this.level +
        '}';
  }

}
