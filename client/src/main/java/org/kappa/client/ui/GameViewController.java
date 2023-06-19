package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.MovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kappa.client.utils.Direction.*;


public class GameViewController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GameViewController.class);

  @FXML
  private Scene gameViewScene;

  @FXML
  private Button gameViewButton;


  @FXML
  private void handleGameViewButton(final MouseEvent event) {
    LOGGER.debug("Button clicked.");
  }


  @FXML
  private void handleKeyRelease(final KeyEvent event) {
    LOGGER.debug("Key released.");

    final var publisher = EventPublisher.getInstance();

    switch (event.getCode()) {
      case UP -> publisher.publishEvent(new MovementEvent("player", UP));
      case DOWN -> publisher.publishEvent(new MovementEvent("player", DOWN));
      case LEFT -> publisher.publishEvent(new MovementEvent("player", LEFT));
      case RIGHT -> publisher.publishEvent(new MovementEvent("player", RIGHT));
      case SPACE -> LOGGER.debug("Shoot.");
      default -> LOGGER.error("GameViewController: Whoot?");
    }
  }

}
