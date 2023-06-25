package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.UrlHelper;

import java.util.Objects;


public class VomitBuilder {

  private static final String[] UP = {"boyup_1.png", "boyup_2.png"};
  private static final String[] DOWN = {"boydown_1.png", "boydown_2.png"};
  private static final String[] LEFT = {"boyleft_1.png", "boyleft_2.png"};
  private static final String[] RIGHT = {"boyright_1.png", "boyright_2.png"};

  private Vomit entity;


  private VomitBuilder() {
  }


  public static VomitBuilder get() {
    final var builder = new VomitBuilder();
    builder.entity = new Vomit();

    return builder;
  }


  public VomitBuilder id(final String uuid) {
    this.entity.id = uuid;

    return this;
  }


  public VomitBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
  }


  public VomitBuilder render(final Direction direction) {
    final var imageView = new ImageView(UrlHelper.getRessourceAsString(
        this.getSpriteDirection(direction)[0])
    );

    return this.render(imageView);
  }


  public VomitBuilder position(final int x, final int y) {
    this.entity.positionComponent = new PositionComponent(x, y);

    return this;
  }


  public VomitBuilder direction(final Direction direction) {
    Objects.requireNonNull(direction);

    this.entity.directionComponent = new DirectionComponent(direction);

    return this;
  }


  public VomitBuilder movement() {
    this.entity.movementAnimationComponent = new MovementAnimationComponent(UP, DOWN, LEFT, RIGHT);

    return this;
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


  public Vomit build() {
    if (!this.isValid()) {
      throw new IllegalArgumentException();
    }

    this.entity.renderComponent.imageView().setX(this.entity.positionComponent.x());
    this.entity.renderComponent.imageView().setY(this.entity.positionComponent.y());

    return this.entity;
  }


  private boolean isValid() {
    return this.entity.id != null
        && this.entity.positionComponent != null
        && this.entity.directionComponent != null
        && this.entity.renderComponent != null
        && this.entity.movementAnimationComponent != null;
  }


  public static class Vomit {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;


    private Vomit() {
    }


    public String getId() {
      return this.id;
    }


    public RenderComponent getRenderComponent() {
      return this.renderComponent;
    }


    public PositionComponent getPositionComponent() {
      return this.positionComponent;
    }


    public DirectionComponent getDirectionComponent() {
      return this.directionComponent;
    }


    public MovementAnimationComponent getMovementAnimationComponent() {
      return this.movementAnimationComponent;
    }

  }

}
