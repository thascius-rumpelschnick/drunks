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

    private final Image sprite;

    public DamageAnimationComponent(final Image sprite) {
        Objects.requireNonNull(sprite);
        this.sprite = sprite;
    }

    //just to set the sprite
    public void show(final ImageView imageView) {
        imageView.setImage(sprite);
    }

    public void animate(final ImageView imageView) {
        javafx.animation.PauseTransition pauseTransition = new javafx.animation.PauseTransition(Duration.seconds(0.1));
        pauseTransition.setOnFinished(e -> animateWithShadow(imageView));
        pauseTransition.play();
    }

    public void animateWithShadow(final ImageView imageView) {
        createShadowEffect(imageView);

        final var animation = createTransition(imageView);
        animation.setOnFinished(event -> {
            imageView.setEffect(null);
            imageView.setImage(sprite);
        });
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
            imageView.setImage(sprite);
        }

        // Create a new instance of AnimationTransition
        public AnimationTransition createTransitionInstance() {
            return new AnimationTransition(imageView);
        }
    }
}
