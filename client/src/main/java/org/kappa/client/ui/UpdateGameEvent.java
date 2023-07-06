package org.kappa.client.ui;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import org.kappa.client.game.GameStats;
import org.kappa.client.system.System;

import java.util.Objects;


public class UpdateGameEvent extends Event {

  public static final EventType<UpdateGameEvent> UPDATE_GAME_EVENT_TYPE = new EventType<>(Event.ANY, "UPDATE_GAME_EVENT");

  private final UpdateGameType updateGameType;
  private final transient GameStats gameStats;


  public UpdateGameEvent(final UpdateGameType updateGameType, final GameStats gameStats, final System source, final Scene target) {
    super(source, target, UPDATE_GAME_EVENT_TYPE);

    Objects.requireNonNull(updateGameType);
    Objects.requireNonNull(gameStats);


    this.updateGameType = updateGameType;
    this.gameStats = gameStats;
  }


  public UpdateGameType getUpdateGameType() {
    return this.updateGameType;
  }


  public GameStats getGameStats() {
    return this.gameStats;
  }
}
