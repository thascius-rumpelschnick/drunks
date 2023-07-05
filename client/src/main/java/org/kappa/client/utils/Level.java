package org.kappa.client.utils;

import java.util.Optional;


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


    @Override
    public Optional<Level> nextLevel() {
      return Optional.of(Level.TWO);
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


    @Override
    public Optional<Level> nextLevel() {
      return Optional.of(Level.THREE);
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


    @Override
    public Optional<Level> nextLevel() {
      return Optional.empty();
    }

  };


  public abstract String getLevelView();

  public abstract int getLevelUpdateInterval();

  public abstract int getLevelCops();

  public abstract int getLevelCopsMaxHealth();

  public abstract Optional<Level> nextLevel();

}
