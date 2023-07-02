package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameController {
    @FXML
    private Button quitButton;
    @FXML
    private Button newGameButton;

    private Stage stage;

    public void initialize() {
        //quitButton.setOnAction(e -> stage.close());
        //newGameButton.setOnAction(e -> DrunksApplicationHelper.startNewGame(stage));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
