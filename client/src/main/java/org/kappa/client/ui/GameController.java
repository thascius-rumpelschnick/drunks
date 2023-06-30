package org.kappa.client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class GameController {
    private Stage stage; // Add a stage variable

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void quitButtonClicked(ActionEvent event) {
        stage.close();
    }
}
