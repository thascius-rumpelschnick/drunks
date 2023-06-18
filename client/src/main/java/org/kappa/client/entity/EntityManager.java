package org.kappa.client.entity;

import org.kappa.client.component.Component;

import java.util.HashMap;
import java.util.Map;


public class EntityManager {

  private final Map<String, Map<Class<? extends Component>, Component>> entityComponentMap;


  public EntityManager() {
    this.entityComponentMap = new HashMap<>();
  }


  public String createEntity(final String entityId) {
    this.entityComponentMap.put(entityId, new HashMap<>());

    return entityId;
  }


  public void putComponent(final String entityId, final Component component) {
    final var componentMap = this.entityComponentMap.get(entityId);
    componentMap.put(component.getClass(), component);
  }


  public <T extends Component> T getComponent(final String entityId, final Class<T> componentClass) {
    final var componentMap = this.entityComponentMap.get(entityId);

    return componentClass.cast(componentMap.get(componentClass));
  }

}
