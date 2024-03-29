package org.kappa.client.component;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.kappa.client.util.LayoutValues;


public record RenderComponent(ImageView imageView) implements Component {

  public RenderComponent(final ImageView imageView) {
    this.imageView = imageView;

    this.initImageView();
  }


  public void update(final PositionComponent positionComponent) {
    this.update(positionComponent.x(), positionComponent.y());
  }


  public void update(final int x, final int y) {
    this.imageView.setLayoutX(x);
    this.imageView.setLayoutY(y);
  }


  private void initImageView() {

    if (this.imageView.getImage().getUrl().startsWith("boy")) {
      this.imageView.setFitWidth(LayoutValues.GAMEBOARD_TILE);
      this.imageView.setFitHeight(LayoutValues.GAMEBOARD_TILE);
    }

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

}
