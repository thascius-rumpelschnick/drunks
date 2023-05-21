package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kappa.client.game.DrunksObservable;

public class GameViewController {

  @FXML
  private Scene gameViewScene;

  @FXML
  private Button gameViewButton;

  @FXML
  private void handleGameViewButton(MouseEvent event) {
    System.out.println("Button clicked.");
  }

  @FXML
  private void handleKeyPress(KeyEvent event) {
    System.out.println("Key pressed.");

    final var drunksObservable = DrunksObservable.getInstance();

    switch (event.getCode()) {
      case UP -> drunksObservable.update(0);
      case DOWN -> drunksObservable.update(1);
      case LEFT -> drunksObservable.update(2);
      case RIGHT -> drunksObservable.update(3);
      case SPACE -> System.out.println("Shoot.");
      default -> System.out.println("GameViewController: Whoot?");
    }
  }

}
