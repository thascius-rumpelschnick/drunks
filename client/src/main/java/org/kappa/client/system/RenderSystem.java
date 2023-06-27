package org.kappa.client.system;

import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EntityCreatedEvent;
import org.kappa.client.event.Listener;
import org.kappa.client.game.Time;
import org.kappa.client.ui.GameView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class RenderSystem implements UpdatableSystem, Listener<EntityCreatedEvent> {

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


  public void setGameView(final GameView gameView) {
    this.gameView = gameView;
  }


  public GameView getGameView() {
    return this.gameView;
  }


  @Override
  public void updateOnEventReceived(final EntityCreatedEvent event) {
    Objects.requireNonNull(event);

    this.addEntityToGameBoard(event.getBody());
  }


  @Override
  public void update(final Time time) {
    Objects.requireNonNull(time);

    if (time.isInInterval()) {
      // final var entities = this.entityManager.filterEntityByComponentType(RenderComponent.class);
      LOGGER.debug("Time is now: {}", time.getElapsedTimeInMilliseconds() / time.getLoopInterval());
    }
  }

}
