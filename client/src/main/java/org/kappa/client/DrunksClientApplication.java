package org.kappa.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kappa.client.game.Game;
import org.kappa.client.game.Player;
import org.kappa.client.utils.Level;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class DrunksClientApplication extends Application {

  public static final String ASSETS_PATH = "images/".replace("/", File.separator);
  public static final int TILE_SIZE = 32;
  private static final int WIDTH = 768;
  private static final int HEIGHT = 576;


  @Override
  public void start(final Stage stage) throws IOException {
    stage.setTitle("Drunks!");
    stage.setResizable(false);

    final var applicationManager = ApplicationManager.getInstance();
    applicationManager.newGame(new Game(new Player("player", "player", 0), Level.ONE, stage));

    stage.show();
    applicationManager.getGame().ifPresent(Game::startGame);
  }


  private void startGameTutorial(final Stage stage) {
    final Pane primaryStageRoot = new Pane();
    final Scene scene = new Scene(primaryStageRoot, WIDTH, HEIGHT);

    // Set the background color
    final String hexColor = "#F2FFF5";
    primaryStageRoot.setStyle("-fx-background-color: " + hexColor + ";");

    stage.setScene(scene);

    // Show welcoming screen
    this.showWelcomeScreen(stage, primaryStageRoot, scene);
    stage.show();
  }


  private void showWelcomeScreen(final Stage primaryStage, final Pane root, final Scene scene) {
    final Image welcomeImage = new Image(getAssetAsStream("Title.png"));
    final ImageView imageView = new ImageView(welcomeImage);

    // Set the position of the ImageView to center it
    final double imageX = (WIDTH - welcomeImage.getWidth() * 0.5) / 2;
    final double imageY = (HEIGHT - welcomeImage.getHeight() * 0.5) / 2;
    imageView.setLayoutX(imageX);
    imageView.setLayoutY(imageY);

    // Set the dimensions of the ImageView to half the size
    final double scaledWidth = welcomeImage.getWidth() * 0.5;
    final double scaledHeight = welcomeImage.getHeight() * 0.5;
    imageView.setFitWidth(scaledWidth);
    imageView.setFitHeight(scaledHeight);

    root.getChildren().add(imageView);

    // Fade out the welcome screen after 10 seconds
    final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
      root.getChildren().remove(imageView);

      // Add background tiles to the primaryStage
      this.addBackgroundTiles(root);

      // Create the sprite and add it to the root pane
      final Sprite sprite = this.createSprite();
      root.getChildren().add(sprite.getImageView());

      // Register key event handlers
      scene.setOnKeyPressed(event2 -> sprite.move(event2.getCode()));

      // Create and show the second stage
      final Stage secondStage = this.createSecondStage();
      secondStage.show();

      // Hide the second stage after 10 seconds
      final Timeline secondTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event3 -> {
        secondStage.hide();
      }));
      secondTimeline.play();
    }));

    timeline.play();
  }


  private Stage createSecondStage() {
    final Stage secondStage = new Stage();
    secondStage.setTitle("Second Stage");

    final Pane secondRoot = new Pane();
    final Scene secondScene = new Scene(secondRoot, WIDTH, HEIGHT);

    // Set the background color
    final String hexColor = "#F2FFF5";
    secondRoot.setStyle("-fx-background-color: " + hexColor + ";");

    secondStage.setScene(secondScene);

    // Load and center the images
    final Image image = new Image(getAssetAsStream("Character.png"));
    final ImageView imageView = new ImageView(image);
    final double imageX = (WIDTH - image.getWidth()) / 2;
    final double imageY = (HEIGHT - image.getHeight()) / 2;
    imageView.setLayoutX(imageX);
    imageView.setLayoutY(imageY);

    secondRoot.getChildren().add(imageView);

    return secondStage;
  }


  private void addBackgroundTiles(final Pane primaryStageRoot) {
    final int numCols = WIDTH / TILE_SIZE;
    final int numRows = HEIGHT / TILE_SIZE;

    for (int col = 0; col < numCols; col++) {
      for (int row = 0; row < numRows; row++) {
        final Rectangle tile = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE,
            TILE_SIZE);
        tile.setFill(Color.web("#F2FFF5"));
        tile.setStroke(Color.DARKGRAY);
        primaryStageRoot.getChildren().add(tile);
      }
    }

    // to add images in the background
      /*  for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                ImageView tileImageView = new ImageView(new Image("shoe.png"));
                tileImageView.setFitWidth(TILE_SIZE);
                tileImageView.setFitHeight(TILE_SIZE);
                tileImageView.setLayoutX(col * TILE_SIZE);
                tileImageView.setLayoutY(row * TILE_SIZE);
                primaryStageRoot.getChildren().add(tileImageView);
            }
        }*/
  }


  private Sprite createSprite() {
    // Load the sprite images
    final var imagePath = ASSETS_PATH + "boyright_1.png";
    final var imageUpPath = ASSETS_PATH + "boyup_1.png";
    final var imageDownPath = ASSETS_PATH + "boydown_1.png";
    final var imageLeftPath = ASSETS_PATH + "boyleft_1.png";
    final var imageRightPath = ASSETS_PATH + "boyright_1.png";
    final var imageLeftRunningPath = ASSETS_PATH + "boyleft_2.png";
    final var imageRightRunningPath = ASSETS_PATH + "boyright_2.png";

    // Create and return the sprite
    return new Sprite(imagePath, imageUpPath, imageDownPath, imageLeftPath, imageRightPath,
        imageLeftRunningPath, imageRightRunningPath, (double) WIDTH / 2, (double) HEIGHT / 2, 10);
  }


  private static InputStream getAssetAsStream(final String assetName) {
    return DrunksClientApplication.class.getResourceAsStream(ASSETS_PATH + assetName);
  }


  public static void main(final String[] args) {
    launch();
  }
}