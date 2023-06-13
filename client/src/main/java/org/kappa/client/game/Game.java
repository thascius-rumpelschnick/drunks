package org.kappa.client.game;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.system.Movement;
import org.kappa.client.ui.BoardView;
import org.kappa.client.ui.GameView;
import org.kappa.client.utils.FXMLHelper;
import org.kappa.client.utils.Level;

import java.io.IOException;

import static org.kappa.client.event.EventType.*;


public class Game {

  private final String player;
  private final Level level;
  private final Stage stage;

  private final EntityManager entityManager;
  private final Movement movementSystem;

  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();

  public Game(final String playerId, final Level level, final Stage stage) throws IOException {
    this.player = playerId;
    this.level = level;
    this.stage = stage;

    // See: org/kappa/client/DrunksApplication.java:42
    final var scene = FXMLHelper.createSceneFromFXML(GameView.FXML_FILE);
    final var board = (Pane) FXMLHelper.createNodeFromFXML(BoardView.FXML_FILE);

    this.entityManager = new EntityManager();
    this.movementSystem = new Movement(this.entityManager);

    this.manageSubscriptions();
  }


  private void manageSubscriptions() {
    PUBLISHER.reset();

    PUBLISHER.subscribe(MOVEMENT, this.movementSystem);
  }

}
