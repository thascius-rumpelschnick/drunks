package org.kappa.client;

import org.kappa.client.game.Game;
import org.kappa.client.game.GameStats;
import org.kappa.client.game.Player;
import org.kappa.client.http.DrunksClient;
import org.kappa.client.system.System;
import org.kappa.client.ui.UpdateGameEvent;
import org.kappa.client.ui.UpdateGameType;

import java.util.Objects;
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
    Objects.requireNonNull(game);

    if (this.game != null) {
      this.game.reset();
    }

    this.game = game;
  }


  public void fireGlobalEvent(
      final UpdateGameType updateGameType,
      final GameStats gameStats,
      final System source
  ) {
    final var scene = this.getGame().orElseThrow().getStage().getScene();
    final var event = new UpdateGameEvent(updateGameType, gameStats, source, scene);

    scene.getRoot().fireEvent(event);
  }


  public Optional<Game> getGame() {
    return Optional.ofNullable(this.game);
  }


  public Optional<GameStats> getGameStats() {
    return Optional.ofNullable(this.getGame().orElseThrow().getGameStats());
  }


  public Optional<Player> getPlayer() {
    return Optional.ofNullable(this.getGame().orElseThrow().getPlayer());
  }


  public DrunksClient getHttpClient() {
    return this.httpClient;
  }
}
