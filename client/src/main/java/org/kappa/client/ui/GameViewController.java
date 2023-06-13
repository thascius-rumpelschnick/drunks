package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.event.MovementEvent;

import static org.kappa.client.utils.Direction.*;


public class GameViewController {

  @FXML
  private Scene gameViewScene;

  @FXML
  private Button gameViewButton;


  @FXML
  private void handleGameViewButton(final MouseEvent event) {
    System.out.println("Button clicked.");
  }


  @FXML
  private void handleKeyPress(final KeyEvent event) {
    System.out.println("Key pressed.");

    final var publisher = EventPublisher.getInstance();

    switch (event.getCode()) {
      case UP -> publisher.publishEvent(new MovementEvent("player", UP));
      case DOWN -> publisher.publishEvent(new MovementEvent("player", DOWN));
      case LEFT -> publisher.publishEvent(new MovementEvent("player", LEFT));
      case RIGHT -> publisher.publishEvent(new MovementEvent("player", RIGHT));
      case SPACE -> System.out.println("Shoot.");
      default -> System.out.println("GameViewController: Whoot?");
    }
  }

}
