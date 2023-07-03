package org.kappa.client.ui;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import org.kappa.client.game.Player;


public class UpdateGameDataEvent extends Event {

  public static final EventType<UpdateGameDataEvent> UPDATE_GAME_DATA_EVENT_TYPE = new EventType<>(Event.ANY, "UPDATE_GAME_DATA_EVENT");

  private final Player player;


  public UpdateGameDataEvent(final Player player, final Object source, final EventTarget target) {
    super(source, target, UPDATE_GAME_DATA_EVENT_TYPE);

    this.player = player;
  }


  public Player getPlayer() {
    return this.player;
  }

}
