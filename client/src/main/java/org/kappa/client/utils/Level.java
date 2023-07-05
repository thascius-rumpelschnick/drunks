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
      return 5;
    }


    @Override
    public int getLevelCopsMaxHealth() {
      return 2;
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
      return 10;
    }


    @Override
    public int getLevelCopsMaxHealth() {
      return 3;
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
      return 15;
    }


    @Override
    public int getLevelCopsMaxHealth() {
      return 5;
    }

  };


  public abstract String getLevelView();

  public abstract int getLevelUpdateInterval();

  public abstract int getLevelCops();

  public abstract int getLevelCopsMaxHealth();

}
