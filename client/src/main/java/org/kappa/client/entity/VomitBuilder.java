package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.*;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.UrlHelper;

import java.util.Objects;


public class VomitBuilder {

  private static final String[] UP = {"vomit/vomit_up.png", "vomit/vomit_up.png"};
  private static final String[] DOWN = {"vomit/vomit_down.png", "vomit/vomit_down.png"};
  private static final String[] LEFT = {"vomit/vomit_left.png", "vomit/vomit_left.png"};
  private static final String[] RIGHT = {"vomit/vomit_right.png", "vomit/vomit_right.png"};

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


  public VomitBuilder velocity(final int velocity) {
    this.entity.velocityComponent = new VelocityComponent(velocity);

    return this;
  }


  public VomitBuilder damage(final int damage) {
    this.entity.damageComponent = new DamageComponent(damage);

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

    this.entity.renderComponent.imageView().setLayoutX(this.entity.positionComponent.x());
    this.entity.renderComponent.imageView().setLayoutY(this.entity.positionComponent.y());

    return this.entity;
  }


  private boolean isValid() {
    return this.entity.id != null
        && this.entity.positionComponent != null
        && this.entity.directionComponent != null
        && this.entity.velocityComponent != null
        && this.entity.damageComponent != null
        && this.entity.renderComponent != null
        && this.entity.movementAnimationComponent != null;
  }


  public static class Vomit {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private VelocityComponent velocityComponent;
    private DamageComponent damageComponent;
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


    public VelocityComponent getVelocityComponent() {
      return this.velocityComponent;
    }


    public DamageComponent getDamageComponent() {
      return this.damageComponent;
    }


    public MovementAnimationComponent getMovementAnimationComponent() {
      return this.movementAnimationComponent;
    }

  }

}
