package org.kappa.client.utils;

public enum Level {
  ONE {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLoopInterval() {
      return 1000;
    }

  },
  TWO {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLoopInterval() {
      return 1000;
    }

  },
  THREE {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLoopInterval() {
      return 1000;
    }

  };


  public abstract String getLevelView();

  /**
   * Loop interval in milliseconds.
   **/
  public abstract int getLoopInterval();

}
