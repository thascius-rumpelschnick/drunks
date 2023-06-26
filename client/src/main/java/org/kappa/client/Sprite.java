package org.kappa.client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.net.URL;

public class Sprite {

  private final ImageView imageView;
  private double x;
  private double y;
  private final double speed;
  private final Image imageUp;
  private final Image imageDown;
  private final Image imageLeft;
  private final Image imageRight;
  private final Image imageLeftRunning;
  private final Image imageRightRunning;

  private boolean isRunningLeft;
  private boolean isRunningRight;

  public Sprite(
      String imagePath,
      String imageUpPath,
      String imageDownPath,
      String imageLeftPath,
      String imageRightPath,
      String imageLeftRunningPath,
      String imageRightRunningPath,
      double x,
      double y,
      double speed
  ) {

    URL imageUrl = this.getClass().getResource(imagePath);
    URL imageUpUrl = this.getClass().getResource(imageUpPath);
    URL imageDownUrl = this.getClass().getResource(imageDownPath);
    URL imageLeftUrl = this.getClass().getResource(imageLeftPath);
    URL imageRightUrl = this.getClass().getResource(imageRightPath);
    URL imageLeftRunningUrl = this.getClass().getResource(imageLeftRunningPath);
    URL imageRightRunningUrl = this.getClass().getResource(imageRightRunningPath);

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
    this.imageView.setLayoutX(x);
    this.imageView.setLayoutY(y);
  }

  public ImageView getImageView() {
    return this.imageView;
  }

  public void move(KeyCode keyCode) {
    double newX = this.x;
    double newY = this.y;

    switch (keyCode) {
      case W:
        newY -= this.speed;
        this.imageView.setImage(this.imageUp);
        break;
      case A:
        newX -= this.speed;
        if (this.isRunningLeft) {
          this.imageView.setImage(this.imageLeftRunning);
        } else {
          this.imageView.setImage(this.imageLeft);
        }
        break;
      case S:
        newY += this.speed;
        this.imageView.setImage(this.imageDown);
        break;
      case D:
        newX += this.speed;
        if (this.isRunningRight) {
          this.imageView.setImage(this.imageRightRunning);
        } else {
          this.imageView.setImage(this.imageRight);
        }
        break;
    }

    // Check if the new position is within the bounds of the pane
    if (newX >= 0 && newX <= this.imageView.getScene().getWidth() - this.imageView.getFitWidth()) {
      this.x = newX;
    }
    if (newY >= 0
        && newY <= this.imageView.getScene().getHeight() - this.imageView.getFitHeight()) {
      this.y = newY;
    }

    // Update the sprite's position
    this.imageView.setLayoutX(this.x);
    this.imageView.setLayoutY(this.y);
  }

  public void setRunningLeft(boolean runningLeft) {
    this.isRunningLeft = runningLeft;
  }

  public void setRunningRight(boolean runningRight) {
    this.isRunningRight = runningRight;
  }
}
