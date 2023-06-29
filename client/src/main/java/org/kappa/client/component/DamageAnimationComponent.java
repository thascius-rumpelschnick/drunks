package org.kappa.client.component;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Objects;


public class DamageAnimationComponent implements Component {

  private String[] sprites;
  private int currentState;
  InputStream currentImage;
  InputStream state0;
  InputStream state1;
  InputStream state2;
  InputStream state3;


  public DamageAnimationComponent() {
  }


  public DamageAnimationComponent(final String[] sprites) {
    Objects.requireNonNull(sprites);

    this.sprites = sprites;
  }


  public DamageAnimationComponent(final InputStream state0, final InputStream state1, final InputStream state2, final InputStream state3) {
    Objects.requireNonNull(state0);
    Objects.requireNonNull(state1);
    Objects.requireNonNull(state2);
    Objects.requireNonNull(state3);

    this.state0 = state0;
    this.state1 = state1;
    this.state2 = state2;
    this.state3 = state3;

    this.currentState = 0;
    this.currentImage = state0;
  }


  public void changeCurrentState() {
    this.currentState = (this.currentState + 1) % 4;
    switch (this.currentState) {
      case 0:
        this.currentImage = this.state0;
        break;
      case 1:
        this.currentImage = this.state1;
        break;
      case 2:
        this.currentImage = this.state2;
        break;
      case 3:
        this.currentImage = this.state3;
        break;
      default:
        this.currentImage = this.state0;
        break;
    }
  }


  public void damageMultipleImages(final ImageView imageView) {
    imageView.setImage(new Image(this.currentImage));
    this.animate(imageView);
    this.changeCurrentState();
  }


  public void damageSingleImage(final ImageView imageView) {
    imageView.setImage(new Image(this.currentImage));
    this.animate(imageView);
  }


  public void animate(final ImageView imageView) {
    final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.1));
    pauseTransition.setOnFinished(e -> this.animateWithShadow(imageView));
    pauseTransition.play();
  }


  public void animateWithShadow(final ImageView imageView) {
    this.createShadowEffect(imageView);

    final var animation = new AnimationTransition(imageView);
    animation.setOnFinished(event -> imageView.setEffect(null));
    animation.play();
  }

  private void createShadowEffect(final ImageView imageView) {
    final DropShadow shadow = new DropShadow();
    shadow.setColor(Color.GRAY);
    shadow.setRadius(15);
    shadow.setSpread(0.5);

    imageView.setEffect(shadow);
  }


  private static class AnimationTransition extends Transition {
    private final ImageView imageView;


    public AnimationTransition(final ImageView imageView) {
      this.imageView = imageView;
      this.setCycleDuration(Duration.millis(300));
      this.setInterpolator(Interpolator.LINEAR);
    }


    @Override
    protected void interpolate(final double frac) {
      // change Images imageView.setImage(sprite);
    }


    public AnimationTransition createTransitionInstance() {
      return new AnimationTransition(this.imageView);
    }
  }
}

