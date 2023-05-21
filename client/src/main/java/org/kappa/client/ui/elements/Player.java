package org.kappa.client.ui.elements;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kappa.client.game.Coordinate;
import org.kappa.client.game.Observer;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.LayoutValues;
import org.kappa.client.utils.UrlHelper;


public class Player implements Observer {

  private Coordinate position;
  private final Direction direction;
  private ImageView imageView;

  private static final String[] UP = {"boyup_1.png", "boyup_2.png"};
  private static final String[] DOWN = {"boydown_1.png", "boydown_2.png"};
  private static final String[] LEFT = {"boyleft_1.png", "boyleft_2.png"};
  private static final String[] RIGHT = {"boyright_1.png", "boyright_2.png"};


  public Player(final Coordinate position, final Direction direction) {
    this.position = position;
    this.direction = direction;

    this.initImageView();
  }


  private void initImageView() {
    this.imageView = new ImageView(UrlHelper.getRessourceAsString(
        this.getSpriteDirection(this.direction)[0])
    );

    this.imageView.setFitWidth(LayoutValues.GAMEBOARD_TILE);
    this.imageView.setFitHeight(LayoutValues.GAMEBOARD_TILE);

    this.imageView.setX(this.position.x());
    this.imageView.setY(this.position.y());

    this.setDropShadow();
  }


  private void setDropShadow() {
    final var dropShadow = new DropShadow(
        BlurType.GAUSSIAN,
        Color.DARKGRAY,
        1,
        1,
        1,
        1
    );

    this.imageView.setEffect(dropShadow);
  }


  public ImageView getImageView() {
    return this.imageView;
  }


  @Override
  public void update(final int notification) {
    System.out.println("PLAYER:" + notification);

    switch (notification) {
      case 0 -> this.moveUp();
      case 1 -> this.moveDown();
      case 2 -> this.moveLeft();
      case 3 -> this.moveRight();
      default -> System.out.println("PLAYER: WHOOT?");
    }

    this.imageView.setX(this.position.x());
    this.imageView.setY(this.position.y());
  }


  private void moveUp() {
    final var destination = new Coordinate(
        this.position.x(),
        this.position.y() - LayoutValues.GAMEBOARD_TILE
    );

    if (this.isOutOfBounds(destination)) {
      return;
    }

    this.animate(Direction.UP);
    this.move(destination);
  }


  private void moveDown() {
    final var destination = new Coordinate(
        this.position.x(),
        this.position.y() + LayoutValues.GAMEBOARD_TILE
    );

    if (this.isOutOfBounds(destination)) {
      return;
    }

    this.animate(Direction.DOWN);
    this.move(destination);
  }


  private void moveLeft() {
    final var destination = new Coordinate(
        this.position.x() - LayoutValues.GAMEBOARD_TILE,
        this.position.y()
    );

    if (this.isOutOfBounds(destination)) {
      return;
    }

    this.animate(Direction.LEFT);
    this.move(destination);
  }


  private void moveRight() {
    final var destination = new Coordinate(
        this.position.x() + LayoutValues.GAMEBOARD_TILE,
        this.position.y()
    );

    if (this.isOutOfBounds(destination)) {
      return;
    }

    this.animate(Direction.RIGHT);
    this.move(destination);
  }


  private boolean isOutOfBounds(final Coordinate destination) {
    // System.out.println("x:" + destination.x() + ", y:" + destination.y());

    return destination.x() < 0
        || destination.x() >= LayoutValues.GAMEBOARD_WITH
        || destination.y() < 0
        || destination.y() >= LayoutValues.GAMEBOARD_HEIGHT;
  }


  private void move(final Coordinate destination) {
    this.position = destination;
  }


  private void animate(final Direction direction) {
    final var animation = new Transition() {
      {
        this.setCycleDuration(Duration.millis(200));
        this.setInterpolator(Interpolator.LINEAR);
      }

      @Override
      protected void interpolate(final double frac) {
        final var a = ((frac * 10) % 2) > 0 ? 1 : 0;

        Player.this.imageView.setImage(
            new Image(
                UrlHelper.getRessourceAsString(
                    Player.this.getSpriteDirection(direction)[a])
            )
        );
      }
    };

    animation.play();
  }


  private String[] getSpriteDirection(final Direction direction) throws IllegalArgumentException {
    final String[] d;

    switch (direction) {
      case UP -> d = UP;
      case DOWN -> d = DOWN;
      case LEFT -> d = LEFT;
      case RIGHT -> d = RIGHT;
      default -> throw new IllegalArgumentException();
    }

    return d;
  }


}
