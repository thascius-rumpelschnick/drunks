package org.kappa.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        // Load the sprite image
        String spriteImagePath = "boyright_1.png";

        // Create the sprite
        Sprite sprite = new Sprite(spriteImagePath, WIDTH / 2, HEIGHT / 2, 8);

        // Add the sprite's ImageView to the root pane
        root.getChildren().add(sprite.getImageView());

        // Register key event handlers
        scene.setOnKeyPressed(event -> sprite.move(event.getCode()));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}