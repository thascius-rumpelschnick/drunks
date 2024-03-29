package org.kappa.client.system;

import org.kappa.client.game.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SystemManager {

  private final Map<Class<? extends System>, System> systems;


  public SystemManager() {
    this.systems = new HashMap<>();
  }


  public void putSystem(final System system) {
    Objects.requireNonNull(system);

    this.systems.put(system.getClass(), system);
  }


  public <T extends System> T getSystem(final Class<T> systemClass) {
    Objects.requireNonNull(systemClass);

    return systemClass.cast(this.systems.get(systemClass));
  }


  public void update(final Timer timer) {
    this.systems.forEach((systemClass, system) -> {
          if (system instanceof final UpdatableSystem updatableSystem) {
            updatableSystem.update(timer);
          }
        }
    );
  }


  public void reset() {
    this.systems.clear();
  }

}
