package org.kappa.client.utils;

public enum Level {
  ONE {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }
  };


  public abstract String getLevelView();

}
