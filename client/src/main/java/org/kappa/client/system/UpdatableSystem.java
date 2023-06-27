package org.kappa.client.system;

import org.kappa.client.game.Time;


public interface UpdatableSystem extends System {

  void update(Time time);

}
