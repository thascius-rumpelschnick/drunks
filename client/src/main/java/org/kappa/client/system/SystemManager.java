package org.kappa.client.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SystemManager {

  private final Map<Class<? extends System>, System> systemMap;


  public SystemManager() {
    this.systemMap = new HashMap<>();
  }


  public void putSystem(final System system) {
    Objects.requireNonNull(system);

    this.systemMap.put(system.getClass(), system);
  }


  public <T extends System> T getSystem(final Class<T> systemClass) {
    Objects.requireNonNull(systemClass);

    return systemClass.cast(this.systemMap.get(systemClass));
  }
}
