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

import java.io.File;
import java.io.IOException;

public class DrunksApplication extends Application {

  public static final int TILE_SIZE = 32;
  private static final int WIDTH = 768;
  private static final int HEIGHT = 576;

  @Override
  public void start(Stage stage) throws IOException {
    //FXMLLoader fxmlLoader = new FXMLLoader(DrunksApplication.class.getResource("hello-view.fxml"));
    //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    //stage.setScene(scene);

    stage.setTitle("Drunks!");
    stage.setResizable(false);

    Pane primaryStageRoot = new Pane();
    Scene scene = new Scene(primaryStageRoot, WIDTH, HEIGHT);

    // Set the background color
    String hexColor = "#F2FFF5";
    primaryStageRoot.setStyle("-fx-background-color: " + hexColor + ";");

    stage.setScene(scene);

    // Show welcoming screen
    showWelcomeScreen(stage, primaryStageRoot, scene);

    stage.show();
  }

  private void showWelcomeScreen(Stage primaryStage, Pane root, Scene scene) {
    Image welcomeImage = new Image("org/kappa/client/Title.png");
    ImageView imageView = new ImageView(welcomeImage);

    // Set the position of the ImageView to center it
    double imageX = (WIDTH - welcomeImage.getWidth() * 0.5) / 2;
    double imageY = (HEIGHT - welcomeImage.getHeight() * 0.5) / 2;
    imageView.setLayoutX(imageX);
    imageView.setLayoutY(imageY);

    // Set the dimensions of the ImageView to half the size
    double scaledWidth = welcomeImage.getWidth() * 0.5;
    double scaledHeight = welcomeImage.getHeight() * 0.5;
    imageView.setFitWidth(scaledWidth);
    imageView.setFitHeight(scaledHeight);

    root.getChildren().add(imageView);

    // Fade out the welcome screen after 10 seconds
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
      root.getChildren().remove(imageView);

      // Add background tiles to the primaryStage
      addBackgroundTiles(root);

      // Create the sprite and add it to the root pane
      Sprite sprite = createSprite();
      root.getChildren().add(sprite.getImageView());

      // Register key event handlers
      scene.setOnKeyPressed(event2 -> sprite.move(event2.getCode()));

      // Create and show the second stage
      Stage secondStage = createSecondStage();
      secondStage.show();

      // Hide the second stage after 10 seconds
      Timeline secondTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event3 -> {
        secondStage.hide();
      }));
      secondTimeline.play();
    }));
    timeline.play();
  }

  private Stage createSecondStage() {
    Stage secondStage = new Stage();
    secondStage.setTitle("Second Stage");

    Pane secondRoot = new Pane();
    Scene secondScene = new Scene(secondRoot, WIDTH, HEIGHT);

    // Set the background color
    String hexColor = "#F2FFF5";
    secondRoot.setStyle("-fx-background-color: " + hexColor + ";");

    secondStage.setScene(secondScene);

    // Load and center the image
    Image image = new Image("org/kappa/client/Character.png");
    ImageView imageView = new ImageView(image);
    double imageX = (WIDTH - image.getWidth()) / 2;
    double imageY = (HEIGHT - image.getHeight()) / 2;
    imageView.setLayoutX(imageX);
    imageView.setLayoutY(imageY);

    secondRoot.getChildren().add(imageView);

    return secondStage;
  }

  private void addBackgroundTiles(Pane primaryStageRoot) {
    int numCols = WIDTH / TILE_SIZE;
    int numRows = HEIGHT / TILE_SIZE;

    for (int col = 0; col < numCols; col++) {
      for (int row = 0; row < numRows; row++) {
        Rectangle tile = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        tile.setFill(Color.web("#F2FFF5"));
        tile.setStroke(Color.DARKGRAY);
        primaryStageRoot.getChildren().add(tile);
      }
    }

    // to add images in the background
      /*  for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                ImageView tileImageView = new ImageView(new Image("Shoe16x16.png"));
                tileImageView.setFitWidth(TILE_SIZE);
                tileImageView.setFitHeight(TILE_SIZE);
                tileImageView.setLayoutX(col * TILE_SIZE);
                tileImageView.setLayoutY(row * TILE_SIZE);
                primaryStageRoot.getChildren().add(tileImageView);
            }
        }*/
  }

  private Sprite createSprite() {
    // Load the sprite image
    final var imgPath = "org/kappa/client/".replace("/", File.separator);

    final var imagePath = imgPath + "boyright_1.png";
    final var imageUpPath = imgPath + "boyup_1.png";
    final var imageDownPath = imgPath + "boydown_1.png";
    final var imageLeftPath = imgPath + "boyleft_1.png";
    final var imageRightPath = imgPath + "boyright_1.png";
    final var imageLeftRunningPath = imgPath + "boyleft_2.png";
    final var imageRightRunningPath = imgPath + "boyright_2.png";

    // Create and return the sprite
    return new Sprite(imagePath, imageUpPath, imageDownPath, imageLeftPath, imageRightPath,
        imageLeftRunningPath, imageRightRunningPath, (double) WIDTH / 2, (double) HEIGHT / 2, 10);
  }

  public static void main(String[] args) {
    launch();
  }
}