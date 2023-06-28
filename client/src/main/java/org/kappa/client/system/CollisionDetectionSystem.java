package org.kappa.client.system;

import org.kappa.client.component.Component;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.utils.LayoutValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;


public class CollisionDetectionSystem implements System {

  private static final Logger LOGGER = LoggerFactory.getLogger(CollisionDetectionSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public CollisionDetectionSystem(final EntityManager entityManager, final SystemManager systemManager) {
    Objects.requireNonNull(entityManager);
    Objects.requireNonNull(systemManager);

    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  public boolean isOutOfBounds(final int x, final int y) {
    return x < 0
        || x >= LayoutValues.GAMEBOARD_WITH
        || y < 0
        || y >= LayoutValues.GAMEBOARD_HEIGHT;
  }

  public Stream<Map.Entry<String, Map<Class<? extends Component>, Component>>> detectCollision(final int x, final int y) {
    return this.entityManager.filterEntityByComponent(new PositionComponent(x,y));
  }

}
