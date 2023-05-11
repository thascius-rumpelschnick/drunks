package org.kappa.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.net.URL;

public class Sprite {
    private ImageView imageView;
    private double x;
    private double y;
    private double speed;

    public Sprite(String imagePath, double x, double y, double speed) {

        URL imageUrl = getClass().getClassLoader().getResource(imagePath);
        if (imageUrl == null) {
            throw new IllegalArgumentException("Image not found: " + imagePath);
        }
        Image image = new Image(imageUrl.toString());
        this.imageView = new ImageView(image);
        this.x = x;
        this.y = y;
        this.speed = speed;

        // Set the initial position of the sprite
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move(KeyCode keyCode) {
        switch (keyCode) {
            case W -> y -= speed;
            case A -> x -= speed;
            case S -> y += speed;
            case D -> x += speed;
        }

        // Update the sprite's position
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }
}
