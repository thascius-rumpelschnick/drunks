package org.kappa.client.component;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

public class DamageAnimationComponent implements Component {

    String[] state0;
    String[] state1;
    String[] state2;
    String[] state3;

    public DamageAnimationComponent(final String[] state0, final String[] state1, final String[] state2, final String[] state3) {
        Objects.requireNonNull(state0);
        Objects.requireNonNull(state1);
        Objects.requireNonNull(state2);
        Objects.requireNonNull(state3);

        this.state0 = state0;
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
    }

    public DamageAnimationComponent() {
    }

    public void animate(final ImageView imageView) {
        javafx.animation.PauseTransition pauseTransition = new javafx.animation.PauseTransition(Duration.seconds(0.1));
        pauseTransition.setOnFinished(e -> animateWithShadow(imageView));
        pauseTransition.play();
    }

    public void animateWithShadow(final ImageView imageView) {
        createShadowEffect(imageView);

        final var animation = createTransition(imageView);
        animation.setOnFinished(event -> imageView.setEffect(null));
        animation.play();
    }

    private AnimationTransition createTransition(final ImageView imageView) {
        return new AnimationTransition(imageView);
    }

    private void createShadowEffect(ImageView imageView) {
        final DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(15);
        shadow.setSpread(0.5);

        imageView.setEffect(shadow);
    }

    private class AnimationTransition extends Transition {
        private ImageView imageView;

        public AnimationTransition(ImageView imageView) {
            this.imageView = imageView;
            this.setCycleDuration(Duration.millis(300));
            this.setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double frac) {
            //change Images imageView.setImage(sprite);
        }

        // Create a new instance of AnimationTransition
        public AnimationTransition createTransitionInstance() {
            return new AnimationTransition(imageView);
        }
    }
}
