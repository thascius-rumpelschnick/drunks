package org.kappa.client.game;

public interface Observable {

  void update(final int notification);
  void register(final Observer observer);
  void unregister(final Observer observer);

}
