package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.*;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.UrlHelper;

import java.util.Objects;


public class DrunkBuilder {

  private static final String[] UP = {"punk/boyup_1.png", "punk/boyup_2.png"};
  private static final String[] DOWN = {"punk/boydown_1.png", "punk/boydown_2.png"};
  private static final String[] LEFT = {"punk/boyleft_1.png", "punk/boyleft_2.png"};
  private static final String[] RIGHT = {"punk/boyright_1.png", "punk/boyright_2.png"};

  private Drunk entity;


  private DrunkBuilder() {
  }


  public static DrunkBuilder get() {
    final var builder = new DrunkBuilder();
    builder.entity = new Drunk();

    return builder;
  }


  public DrunkBuilder id(final String uuid) {
    this.entity.id = uuid;

    return this;
  }

  public DrunkBuilder health(final int health) {
    this.entity.healthComponent = new HealthComponent(health);

    return this;
  }

  public DrunkBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
  }


  public DrunkBuilder render(final Direction direction) {
    final var imageView = new ImageView(UrlHelper.getRessourceAsString(
        this.getSpriteDirection(direction)[0])
    );

    return this.render(imageView);
  }


  public DrunkBuilder position(final int x, final int y) {
    this.entity.positionComponent = new PositionComponent(x, y);

    return this;
  }


  public DrunkBuilder direction(final Direction direction) {
    Objects.requireNonNull(direction);

    this.entity.directionComponent = new DirectionComponent(direction);

    return this;
  }


  public DrunkBuilder velocity(final int velocity) {
    this.entity.velocityComponent = new VelocityComponent(velocity);

    return this;
  }


  public DrunkBuilder movement() {
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


  public Drunk build() {
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
        && this.entity.renderComponent != null
        && this.entity.movementAnimationComponent != null;
  }


  public static class Drunk {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private VelocityComponent velocityComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;

    private HealthComponent healthComponent;

    private Drunk() {
    }

    public HealthComponent getHealthComponent() {
      return healthComponent;
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


    public MovementAnimationComponent getMovementAnimationComponent() {
      return this.movementAnimationComponent;
    }

  }

}
