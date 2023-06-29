package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kappa.client.ApplicationManager;
import org.kappa.client.event.AttackEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.MovementEvent;
import org.kappa.client.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kappa.client.utils.AttackType.VOMIT;
import static org.kappa.client.utils.Direction.*;


public class GameViewController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GameViewController.class);
  private static final ApplicationManager APPLICATION_MANAGER = ApplicationManager.getInstance();

  @FXML
  private Scene gameViewScene;

  @FXML
  private Button gameViewButton;


  @FXML
  private void handleGameViewButton(final MouseEvent event) {
    LOGGER.debug("Button clicked.");

    APPLICATION_MANAGER.getGame().ifPresentOrElse(
        Game::stopGame,
        RuntimeException::new
    );
  }


  @FXML
  private void handleKeyRelease(final KeyEvent event) {
    // LOGGER.debug("Key released.");

    final var publisher = EventPublisher.getInstance();
    final var game = APPLICATION_MANAGER.getGame().orElseThrow();
    final var playerId = game.getPlayer().getId();

    switch (event.getCode()) {
      case UP -> publisher.publishEvent(new MovementEvent(playerId, UP));
      case DOWN -> publisher.publishEvent(new MovementEvent(playerId, DOWN));
      case LEFT -> publisher.publishEvent(new MovementEvent(playerId, LEFT));
      case RIGHT -> publisher.publishEvent(new MovementEvent(playerId, RIGHT));
      case SPACE -> publisher.publishEvent(new AttackEvent(playerId, VOMIT));
      default -> LOGGER.error("GameViewController: Whoot?");
    }
  }

}
