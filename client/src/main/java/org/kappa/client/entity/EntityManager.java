package org.kappa.client.entity;

import org.kappa.client.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class EntityManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);

  private final Map<String, Map<Class<? extends Component>, Component>> entityComponentMap;


  public EntityManager() {
    this.entityComponentMap = new HashMap<>();
  }


  public String createEntity(final String entityId) {
    Objects.requireNonNull(entityId);

    this.entityComponentMap.put(entityId, new HashMap<>());

    return entityId;
  }


  public void putComponent(final String entityId, final Component component) {
    Objects.requireNonNull(entityId);
    Objects.requireNonNull(component);

    final var componentMap = this.entityComponentMap.get(entityId);
    componentMap.put(component.getClass(), component);
  }


  public <T extends Component> T getComponent(final String entityId, final Class<T> componentClass) {
    Objects.requireNonNull(entityId);
    Objects.requireNonNull(componentClass);

    final var componentMap = this.entityComponentMap.get(entityId);

    return componentClass.cast(componentMap.get(componentClass));
  }


  public Optional<Map.Entry<String, Map<Class<? extends Component>, Component>>> filterEntityByComponent(final Component component) {
    return this.entityComponentMap
        .entrySet()
        .stream()
        .filter(
            (final Map.Entry<String, Map<Class<? extends Component>, Component>> entry) -> {
              try {
                final var components = entry.getValue();
                final var entityComponent = components.get(component.getClass());

                return component.equals(entityComponent);
              } catch (final Exception exception) {
                LOGGER.error(exception.getMessage(), exception);

                return false;
              }
            }
        )
        .findFirst();
  }


  @Override
  public String toString() {
    return "EntityManager{" +
        "entityComponentMap=" + this.entityComponentMap +
        '}';
  }
}
