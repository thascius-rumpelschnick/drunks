package org.kappa.client.http;

public record User(String username, String password) {

  @Override
  public String toString() {
    return "{" +
        "username:'" + this.username + '\'' +
        ", password=:" + this.password + '\'' +
        '}';
  }

}
