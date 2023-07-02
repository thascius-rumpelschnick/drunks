package org.kappa.client;

import org.kappa.client.game.Game;
import org.kappa.client.http.DrunksClient;

import java.util.Optional;


public class ApplicationManager {

  private static ApplicationManager applicationManager;

  private Game game;

  private final DrunksClient httpClient = DrunksClient.create();


  private ApplicationManager() {
    // Must not be invoked directly
  }


  public static ApplicationManager getInstance() {
    if (applicationManager == null) {
      applicationManager = new ApplicationManager();
    }

    return applicationManager;
  }


  public void newGame(final Game game) {
    this.game = game;
  }


  public Optional<Game> getGame() {
    return Optional.ofNullable(this.game);
  }


  public DrunksClient getHttpClient() {
    return this.httpClient;
  }
}
