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
    private Image imageUp;
    private Image imageDown;
    private Image imageLeft;
    private Image imageRight;
    private Image imageLeftRunning;
    private Image imageRightRunning;

    private boolean isRunningLeft;
    private boolean isRunningRight;

    public Sprite(String imagePath, String imageUpPath, String imageDownPath, String imageLeftPath, String imageRightPath,
                  String imageLeftRunningPath, String imageRightRunningPath,
                  double x, double y, double speed) {

        URL imageUrl = getClass().getClassLoader().getResource(imagePath);
        URL imageUpUrl = getClass().getClassLoader().getResource(imageUpPath);
        URL imageDownUrl = getClass().getClassLoader().getResource(imageDownPath);
        URL imageLeftUrl = getClass().getClassLoader().getResource(imageLeftPath);
        URL imageRightUrl = getClass().getClassLoader().getResource(imageRightPath);
        URL imageLeftRunningUrl = getClass().getClassLoader().getResource(imageLeftRunningPath);
        URL imageRightRunningUrl = getClass().getClassLoader().getResource(imageRightRunningPath);

        if (imageUrl == null || imageUpUrl == null || imageDownUrl == null || imageLeftUrl == null ||
                imageRightUrl == null || imageLeftRunningUrl == null || imageRightRunningUrl == null) {
            throw new IllegalArgumentException("Image not found");
        }

        Image image = new Image(imageUrl.toString());
        this.imageUp = new Image(imageUpUrl.toString());
        this.imageDown = new Image(imageDownUrl.toString());
        this.imageLeft = new Image(imageLeftUrl.toString());
        this.imageRight = new Image(imageRightUrl.toString());
        this.imageLeftRunning = new Image(imageLeftRunningUrl.toString());
        this.imageRightRunning = new Image(imageRightRunningUrl.toString());

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
        double newX = x;
        double newY = y;

        switch (keyCode) {
            case W:
                newY -= speed;
                imageView.setImage(imageUp);
                break;
            case A:
                newX -= speed;
                if (isRunningLeft)
                    imageView.setImage(imageLeftRunning);
                else
                    imageView.setImage(imageLeft);
                break;
            case S:
                newY += speed;
                imageView.setImage(imageDown);
                break;
            case D:
                newX += speed;
                if (isRunningRight)
                    imageView.setImage(imageRightRunning);
                else
                    imageView.setImage(imageRight);
                break;
        }

        // Check if the new position is within the bounds of the pane
        if (newX >= 0 && newX <= imageView.getScene().getWidth() - imageView.getFitWidth()) {
            x = newX;
        }
        if (newY >= 0 && newY <= imageView.getScene().getHeight() - imageView.getFitHeight()) {
            y = newY;
        }

        // Update the sprite's position
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }

    public void setRunningLeft(boolean runningLeft) {
        isRunningLeft = runningLeft;
    }

    public void setRunningRight(boolean runningRight) {
        isRunningRight = runningRight;
    }
}
