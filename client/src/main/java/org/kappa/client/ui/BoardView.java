package org.kappa.client.ui;

import javafx.scene.layout.Pane;

public class BoardView {

  public static final String FXML_FILE = "board-view.fxml";

  private final Pane board;

  public BoardView(final Pane board) {
    this.board = board;
  }

  public Pane getBoard() {
    return this.board;
  }

}
