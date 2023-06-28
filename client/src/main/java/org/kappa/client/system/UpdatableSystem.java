package org.kappa.client.system;

import org.kappa.client.game.Timer;


public interface UpdatableSystem extends System {

  void update(Timer timer);

}
