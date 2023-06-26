package org.kappa.client.ui;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView {

  public static final String FXML_FILE = "game-view.fxml";
  private final BoardView board;
  private final Scene scene;

  public GameView(final BoardView boardView, final Scene scene) {
    this.board = boardView;
    this.scene = scene;

    this.initializeGameView();
  }

  private void initializeGameView() {
    final var parent = (VBox) this.scene.getRoot();
    final var hBox = (HBox) parent.getChildren().get(1);

    hBox.getChildren().remove(0);
    hBox.getChildren().add(0, this.board.getBoard());
  }

  public Scene getScene() {
    return this.scene;
  }

  public BoardView getBoard() {
    return this.board;
  }

}
