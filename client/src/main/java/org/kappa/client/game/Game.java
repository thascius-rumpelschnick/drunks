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
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.FXMLHelper;
import org.kappa.client.utils.IdHelper;
import org.kappa.client.utils.LayoutValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.System;

import static org.kappa.client.event.EventType.*;


public class Game {

  private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
  private static final EventPublisher PUBLISHER = EventPublisher.getInstance();

  private final Player player;
  private final GameStats gameStats;
  private final Timer timer;
  private AnimationTimer animationTimer;
  private final Stage stage;
  private final EntityManager entityManager;
  private final SystemManager systemManager;


  public Game(final Player player, final GameStats gameStats, final Stage stage) throws IOException {
    this.player = player;
    this.gameStats = gameStats;
    this.timer = new Timer(gameStats.getLevel().getLevelUpdateInterval(), System.nanoTime());

    this.stage = stage;

    this.entityManager = new EntityManager();
    this.systemManager = new SystemManager();

    this.manageSystems();
    this.manageSubscriptions();

    this.initializeGame();
  }


  private void manageSystems() {
    this.systemManager.reset();

    this.systemManager.putSystem(new RenderSystem(this.entityManager, this.systemManager));
    this.systemManager.putSystem(new AttackSystem(this.entityManager, this.systemManager));
    this.systemManager.putSystem(new AnimationSystem(this.entityManager, this.systemManager));
    this.systemManager.putSystem(new MovementSystem(this.entityManager, this.systemManager));
    this.systemManager.putSystem(new HealthSystem(this.entityManager, this.systemManager));
    this.systemManager.putSystem(new CollisionDetectionSystem(this.entityManager, this.systemManager));

    this.systemManager.putSystem(new NonPlayerEntitySystem(this.entityManager, this.systemManager, this.gameStats.getLevel()));
  }


  private void manageSubscriptions() {
    PUBLISHER.reset();

    PUBLISHER.subscribe(MOVEMENT, this.systemManager.getSystem(MovementSystem.class));
    PUBLISHER.subscribe(ATTACK, this.systemManager.getSystem(AttackSystem.class));
    PUBLISHER.subscribe(ENTITY_CREATED, this.systemManager.getSystem(RenderSystem.class));
    PUBLISHER.subscribe(ENTITY_REMOVED, this.systemManager.getSystem(RenderSystem.class));
    PUBLISHER.subscribe(DAMAGE, this.systemManager.getSystem(HealthSystem.class));

    PUBLISHER.subscribe(ENTITY_CREATED, this.systemManager.getSystem(NonPlayerEntitySystem.class));
    PUBLISHER.subscribe(ENTITY_REMOVED, this.systemManager.getSystem(NonPlayerEntitySystem.class));
  }


  private void initializeGame() throws IOException {
    final var scene = FXMLHelper.createSceneFromFXML(GameView.FXML_FILE);
    final var board = (Pane) FXMLHelper.createParentFromFXML(this.gameStats.getLevel().getLevelView());

    this.systemManager.getSystem(RenderSystem.class).setGameView(new GameView(new BoardView(board), scene));

    this.parseBoardElements(board);
    this.addPlayerToGame();

    this.stage.setScene(this.systemManager.getSystem(RenderSystem.class).getGameView().getScene());

    this.animationTimer = new AnimationTimer() {
      @Override
      public void handle(final long now) {
        Game.this.update(now);
      }
    };
  }


  private void update(final long now) {
    if (this.timer.update(now)) {
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


  public Player getPlayer() {
    return this.player;
  }


  private void addPlayerToGame() {
    final var drunk = DrunkBuilder
        .get()
        .id(this.player.getId())
        .health(this.gameStats.getPlayerHealth())
        .render(Direction.UP)
        .direction(Direction.UP)
        .position(0, LayoutValues.GAMEBOARD_HEIGHT - LayoutValues.GAMEBOARD_TILE)
        .velocity(LayoutValues.GAMEBOARD_TILE)
        .movementAnimation()
        .damageAnimation()
        .build();

    this.entityManager.createEntity(drunk.getId());
    this.entityManager.putComponent(drunk.getId(), drunk.getHealthComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getRenderComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getDirectionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getPositionComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getVelocityComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getMovementAnimationComponent());
    this.entityManager.putComponent(drunk.getId(), drunk.getDamageAnimationComponent());

    this.systemManager.getSystem(RenderSystem.class).addEntityToGameBoard(drunk.getId());
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
