package org.kappa.client.system;

import org.kappa.client.component.RenderComponent;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.ui.GameView;

import java.util.Objects;


public class RenderSystem implements  System{

  private final EntityManager entityManager;
  private final SystemManager systemManager;

  private GameView gameView;


  public RenderSystem(EntityManager entityManager, SystemManager systemManager) {
    this.entityManager = entityManager;
    this.systemManager = systemManager;
  }


  public void addEntityToGameBoard(final String entityId) {
    Objects.requireNonNull(this.gameView);

    final RenderComponent renderComponent = this.entityManager.getComponent(entityId, RenderComponent.class);
    final var boardView = this.gameView.getBoard();

    boardView.getBoard().getChildren().add(renderComponent.imageView());
  }


  public void setGameView(GameView gameView) {
    this.gameView = gameView;
  }


  public GameView getGameView() {
    return gameView;
  }


  @Override
  public void update() {

  }

}
