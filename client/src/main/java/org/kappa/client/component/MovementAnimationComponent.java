package org.kappa.client.component;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.UrlHelper;

import java.util.Objects;


public class MovementAnimationComponent implements Component {

  private final String[] up;
  private final String[] down;
  private final String[] left;
  private final String[] right;


  public MovementAnimationComponent(final String[] up, final String[] down, final String[] left, final String[] right) {
    Objects.requireNonNull(up);
    Objects.requireNonNull(down);
    Objects.requireNonNull(left);
    Objects.requireNonNull(right);

    this.up = up;
    this.down = down;
    this.left = left;
    this.right = right;
  }


  public void animate(final ImageView imageView, final Direction direction) {
    final var animation = new Transition() {
      {
        this.setCycleDuration(Duration.millis(200));
        this.setInterpolator(Interpolator.LINEAR);
      }

      @Override
      protected void interpolate(final double frac) {
        final var index = ((frac * 10) % 2) > 0 ? 1 : 0;

        imageView.setImage(
            new Image(
                UrlHelper.getRessourceAsString(
                    MovementAnimationComponent.this.getSpriteDirection(direction)[index])
            )
        );
      }
    };

    animation.play();
  }


  private String[] getSpriteDirection(final Direction direction) throws IllegalArgumentException {
    final String[] d;

    switch (direction) {
      case UP -> d = this.up;
      case DOWN -> d = this.down;
      case LEFT -> d = this.left;
      case RIGHT -> d = this.right;
      default -> throw new IllegalArgumentException();
    }

    return d;
  }

}
