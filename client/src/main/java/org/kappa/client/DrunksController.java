package org.kappa.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DrunksController {

  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to Drunks Application!");
  }
}