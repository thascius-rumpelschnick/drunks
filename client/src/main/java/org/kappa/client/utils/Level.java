package org.kappa.client.utils;

public enum Level {
  ONE {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLevelUpdateInterval() {
      return 10;
    }


    @Override
    public int getLevelCops() {
      return 0;
    }

  },
  TWO {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLevelUpdateInterval() {
      return 10;
    }


    @Override
    public int getLevelCops() {
      return 0;
    }

  },
  THREE {
    @Override
    public String getLevelView() {
      return "board-view.fxml";
    }


    @Override
    public int getLevelUpdateInterval() {
      return 10;
    }


    @Override
    public int getLevelCops() {
      return 0;
    }

  };


  public abstract String getLevelView();

  public abstract int getLevelUpdateInterval();

  public abstract int getLevelCops();

}
