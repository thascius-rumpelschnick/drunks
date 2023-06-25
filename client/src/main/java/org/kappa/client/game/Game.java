package org.kappa.client.game;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kappa.client.entity.DrunkBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.system.AttackSystem;
import org.kappa.client.system.MovementSystem;
import org.kappa.client.system.RenderSystem;
import org.kappa.client.system.SystemManager;
import org.kappa.client.ui.BoardView;
import org.kappa.client.ui.GameView;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.FXMLHelper;
import org.kappa.client.utils.LayoutValues;
import org.kappa.client.utils.Level;

import java.io.IOException;

import static org.kappa.client.event.EventType.ATTACK;
import static org.kappa.client.event.EventType.MOVEMENT;


public class Game {

  private final String player;
  private final Level level;
  private final Stage stage;

  private final EntityManager entityManager;
  private final SystemManager systemManager;

  private final RenderSystem renderSystem;
  private final MovementSystem movementSystem;
  private final AttackSystem attackSystem;

  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();


  public Game(final String playerId, final Level level, final Stage stage) throws IOException {
    this.player = playerId;
    this.level = level;
    this.stage = stage;

    this.entityManager = new EntityManager();
    this.systemManager = new SystemManager();


    this.renderSystem = new RenderSystem(this.entityManager, this.systemManager);
    this.attackSystem = new AttackSystem(this.entityManager, this.systemManager);
    this.movementSystem = new MovementSystem(this.entityManager);

    this.manageSystems();
    this.manageSubscriptions();
    this.initializeGame();
  }


  private void manageSystems() {
    this.systemManager.putSystem(this.renderSystem);
  }


  private void initializeGame() throws IOException {
    // See: org/kappa/client/DrunksApplication.java:42
    final var scene = FXMLHelper.createSceneFromFXML(GameView.FXML_FILE);
    final var board = (Pane) FXMLHelper.createNodeFromFXML(BoardView.FXML_FILE);

    final var drunk = DrunkBuilder
        .get()
        .id(this.player)
        .render(Direction.UP)
        .direction(Direction.UP)
        .position(0, LayoutValues.GAMEBOARD_HEIGHT - LayoutValues.GAMEBOARD_TILE)
        .movement()
        .build();

    this.entityManager.createEntity(drunk.getId());
    this.entityManager.putComponent(drunk.getId(), drunk.getRenderComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getDirectionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getPositionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getMovementAnimationComponent());

    final var gameView = new GameView(new BoardView(board), scene);
    this.renderSystem.setGameView(gameView);
    this.renderSystem.addEntityToGameBoard(drunk.getId());

    this.stage.setScene(this.renderSystem.getGameView().getScene());
  }


  private void manageSubscriptions() {
    PUBLISHER.reset();

    PUBLISHER.subscribe(MOVEMENT, this.movementSystem);
    PUBLISHER.subscribe(ATTACK, this.attackSystem);
  }


}
