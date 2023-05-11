package org.kappa.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DrunksApplication extends Application {

    public static final int TILE_SIZE = 16;
    private static final int WIDTH = 768;
    private static final int HEIGHT = 576;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("2D Game");
        primaryStage.setResizable(false);

        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Set the background color
        String hexColor = "#F2FFF5";
        root.setStyle("-fx-background-color: " + hexColor + ";");

        primaryStage.setScene(scene);

        // Add your game elements to the root pane

        // Show welcoming screen
        showWelcomeScreen(primaryStage, root, scene);

        primaryStage.show();
    }

    private void showWelcomeScreen(Stage primaryStage, Pane root, Scene scene) {
        Image welcomeImage = new Image("Title.png");
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
            primaryStage.setScene(scene);

            // Create the sprite and add it to the root pane
            Sprite sprite = createSprite();
            root.getChildren().add(sprite.getImageView());

            // Register key event handlers
            scene.setOnKeyPressed(event2 -> sprite.move(event2.getCode()));
        }));
        timeline.play();
    }

    private Sprite createSprite() {
        // Load the sprite image
        String imagePath = "boyright_1.png";
        String imageUpPath = "boyup_1.png";
        String imageDownPath = "boydown_1.png";
        String imageLeftPath = "boyleft_1.png";
        String imageRightPath = "boyright_1.png";
        String imageLeftRunningPath = "boyleft_2.png";
        String imageRightRunningPath = "boyright_2.png";

        // Create and return the sprite
        return new Sprite(imagePath, imageUpPath, imageDownPath, imageLeftPath, imageRightPath,
                imageLeftRunningPath, imageRightRunningPath, (double) WIDTH / 2, (double) HEIGHT / 2, 10);
    }

    public static void main(String[] args) {
        launch();
    }
}