package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class BoardViewController {

  @FXML
  private Pane boardView;

  @FXML
  private void handleKeyPress(final KeyEvent event) {
    System.out.println(event.getTarget().toString());
  }

}
