package org.kappa.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * ToDo: Remove this and org/kappa/client/hello-view.fxml + org/kappa/client/drunks-view.fxml as well
 */
@Deprecated
public class DrunksController {

  @FXML
  private Label welcomeText;


  @FXML
  protected void onHelloButtonClick() {
    this.welcomeText.setText("Welcome to Drunks Application!");
  }
}