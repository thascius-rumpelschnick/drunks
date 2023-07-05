package org.kappa.client.system;

import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EntityEvent;
import org.kappa.client.event.Listener;
import org.kappa.client.ui.GameView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.kappa.client.event.EventType.ENTITY_CREATED;
import static org.kappa.client.event.EventType.ENTITY_REMOVED;


public class RenderSystem implements System, Listener<EntityEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RenderSystem.class);

  private final EntityManager entityManager;
  private final SystemManager systemManager;

  private GameView gameView;


  public RenderSystem(final EntityManager entityManager, final SystemManager systemManager) {
    Objects.requireNonNull(entityManager);
    Objects.requireNonNull(systemManager);

    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  public void addEntityToGameBoard(final String entityId) {
    Objects.requireNonNull(entityId);
    Objects.requireNonNull(this.gameView);

    final var renderComponent = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var positionComponent = this.entityManager.getComponent(entityId, PositionComponent.class);
    final var boardView = this.gameView.getBoard();

    renderComponent.update(positionComponent.x(), positionComponent.y());
    boardView.getBoard().getChildren().add(renderComponent.imageView());
  }


  public void removeEntityFromGameBoard(final String entityId) {
    Objects.requireNonNull(entityId);
    Objects.requireNonNull(this.gameView);

    final var renderComponent = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var boardView = this.gameView.getBoard();

    boardView.getBoard().getChildren().remove(renderComponent.imageView());
  }


  public void setGameView(final GameView gameView) {
    this.gameView = gameView;
  }


  @Override
  public void updateOnEventReceived(final EntityEvent event) {
    Objects.requireNonNull(event);

    if (ENTITY_CREATED == event.getEventType()) {
      this.addEntityToGameBoard(event.getBody());

    } else if (ENTITY_REMOVED == event.getEventType()) {
      this.removeEntityFromGameBoard(event.getBody());

    } else {
      LOGGER.error("Event: {}", event);
      throw new IllegalArgumentException();
    }

  }

}
