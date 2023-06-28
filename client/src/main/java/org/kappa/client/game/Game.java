package org.kappa.client.game;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kappa.client.entity.DrunkBuilder;
import org.kappa.client.entity.EntityManager;
import org.kappa.client.entity.WallBuilder;
import org.kappa.client.entity.WaterBuilder;
import org.kappa.client.event.EventPublisher;
import org.kappa.client.system.*;
import org.kappa.client.ui.BoardView;
import org.kappa.client.ui.GameView;
import org.kappa.client.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.System;

import static org.kappa.client.event.EventType.*;


public class Game {

  private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();

  private final String player;
  private final Level level;
  private final Timer timer;
  private AnimationTimer animationTimer;
  private final Stage stage;

  private final EntityManager entityManager;
  private final SystemManager systemManager;

  private final RenderSystem renderSystem;
  private final AttackSystem attackSystem;
  private final AnimationSystem animationSystem;
  private final MovementSystem movementSystem;
  private final HealthSystem healthSystem;
  private final CollisionDetectionSystem collisionDetectionSystem;

  private final NonPlayerEntitySystem nonPlayerEntitySystem;


  public Game(final String playerId, final Level level, final Stage stage) throws IOException {
    this.player = playerId;
    this.level = level;
    this.timer = new Timer(level.getLevelUpdateInterval(), System.nanoTime());
    // LOGGER.debug("StartTime: {}", this.timer.getLapTime());
    this.stage = stage;

    this.entityManager = new EntityManager();
    this.systemManager = new SystemManager();

    this.renderSystem = new RenderSystem(this.entityManager, this.systemManager);
    this.attackSystem = new AttackSystem(this.entityManager, this.systemManager);
    this.collisionDetectionSystem = new CollisionDetectionSystem(this.entityManager, this.systemManager);
    this.animationSystem = new AnimationSystem(this.entityManager, this.systemManager);
    this.movementSystem = new MovementSystem(this.entityManager, this.systemManager);
    this.healthSystem = new HealthSystem(this.entityManager, this.systemManager);
    this.nonPlayerEntitySystem = new NonPlayerEntitySystem(this.entityManager, this.systemManager);

    this.manageSystems();
    this.manageSubscriptions();
    this.initializeGame();
  }


  private void manageSystems() {
    this.systemManager.putSystem(this.renderSystem);
    this.systemManager.putSystem(this.collisionDetectionSystem);
    this.systemManager.putSystem(this.animationSystem);
    this.systemManager.putSystem(this.movementSystem);

    this.systemManager.putSystem(this.nonPlayerEntitySystem);
  }


  private void manageSubscriptions() {
    PUBLISHER.reset();

    PUBLISHER.subscribe(MOVEMENT, this.movementSystem);
    PUBLISHER.subscribe(ATTACK, this.attackSystem);
    PUBLISHER.subscribe(ENTITY_CREATED, this.renderSystem);
    PUBLISHER.subscribe(ENTITY_REMOVED, this.renderSystem);
    PUBLISHER.subscribe(DAMAGE, this.healthSystem);

    PUBLISHER.subscribe(ENTITY_CREATED, this.nonPlayerEntitySystem);
  }


  private void initializeGame() throws IOException {
    final var scene = FXMLHelper.createSceneFromFXML(GameView.FXML_FILE);
    final var board = (Pane) FXMLHelper.createNodeFromFXML(this.level.getLevelView());

    this.renderSystem.setGameView(new GameView(new BoardView(board), scene));

    this.parseBoardElements(board);
    this.addPlayerToGame();

    this.stage.setScene(this.renderSystem.getGameView().getScene());

    this.animationTimer = new AnimationTimer() {
      @Override
      public void handle(final long now) {
        Game.this.update(now);
      }
    };
  }


  private void update(final long now) {
    if (this.timer.update(now)) {
      // LOGGER.debug("NOW: {}\nROUND: {}", now, this.timer.getRound());
      this.systemManager.update(this.timer);
    }
  }


  public void startGame() {
    LOGGER.debug("startGame");

    this.animationTimer.start();
  }


  public void stopGame() {
    LOGGER.debug("stopGame");

    this.animationTimer.stop();
  }


  private void addPlayerToGame() {
    final var drunk = DrunkBuilder
        .get()
        .id(this.player)
        .health(5)
        .render(Direction.UP)
        .direction(Direction.UP)
        .position(0, LayoutValues.GAMEBOARD_HEIGHT - LayoutValues.GAMEBOARD_TILE)
        .velocity(LayoutValues.GAMEBOARD_TILE)
        .movement()
        .build();

    this.entityManager.createEntity(drunk.getId());
    this.entityManager.putComponent(drunk.getId(), drunk.getRenderComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getDirectionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getPositionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getVelocityComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getMovementAnimationComponent());

    this.renderSystem.addEntityToGameBoard(drunk.getId());
  }


  private void parseBoardElements(final Pane board) {
    for (final Node node : board.getChildren()) {
      if (node instanceof final ImageView imageView && imageView.getImage() != null) {
        this.addBoardElementsToEntityManager(imageView);
      }
    }
  }


  private void addBoardElementsToEntityManager(final ImageView imageView) {
    FXMLHelper.validateLayoutPosition(imageView.getLayoutX(), imageView.getLayoutY());

    if (imageView.getImage().getUrl().endsWith("wall1.png")) {
      final var wall = WallBuilder
          .get()
          .id(IdHelper.createRandomUuid())
          .render(imageView)
          .position((int) imageView.layoutXProperty().get(), (int) imageView.layoutYProperty().get())
          .health(4)
          .damage()
          .build();

      this.entityManager.createEntity(wall.getId());
      this.entityManager.putComponent(wall.getId(), wall.getPositionComponent());
      this.entityManager.putComponent(wall.getId(), wall.getRenderComponent());
      this.entityManager.putComponent(wall.getId(), wall.getHealthComponent());
      this.entityManager.putComponent(wall.getId(), wall.getDamageAnimationComponent());

    } else if (imageView.getImage().getUrl().endsWith("wall2.png")) {
      final var water = WaterBuilder
          .get()
          .render(imageView)
          .id(IdHelper.createRandomUuid())
          .position((int) imageView.layoutXProperty().get(), (int) imageView.layoutYProperty().get())
          .build();

      this.entityManager.createEntity(water.getId());
      this.entityManager.putComponent(water.getId(), water.getPositionComponent());
      this.entityManager.putComponent(water.getId(), water.getRenderComponent());
    }
  }

}
