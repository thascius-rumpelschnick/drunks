package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.*;
import org.kappa.client.utils.Direction;
import org.kappa.client.utils.UrlHelper;

import java.util.Objects;


public class CopBuilder {

  private static final String[] UP = {"enemy/copup_1.png", "enemy/copup_1.png"};
  private static final String[] DOWN = {"enemy/copdown_1.png", "enemy/copdown_1.png"};
  private static final String[] LEFT = {"enemy/copleft_1.png", "enemy/copleft_1.png"};
  private static final String[] RIGHT = {"enemy/copright_1.png", "enemy/copright_1.png"};

  private Cop entity;


  private CopBuilder() {
  }


  public static CopBuilder get() {
    final var builder = new CopBuilder();
    builder.entity = new Cop();

    return builder;
  }


  public CopBuilder id(final String uuid) {
    this.entity.id = uuid;

    return this;
  }


  public CopBuilder health(final int health) {
    this.entity.healthComponent = new HealthComponent(health);

    return this;
  }


  public CopBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
  }


  public CopBuilder render(final Direction direction) {
    final var imageView = new ImageView(UrlHelper.getRessourceAsString(
        this.getSpriteDirection(direction)[0])
    );

    return this.render(imageView);
  }


  public CopBuilder position(final int x, final int y) {
    this.entity.positionComponent = new PositionComponent(x, y);

    return this;
  }


  public CopBuilder direction(final Direction direction) {
    Objects.requireNonNull(direction);

    this.entity.directionComponent = new DirectionComponent(direction);

    return this;
  }


  public CopBuilder velocity(final int velocity) {
    this.entity.velocityComponent = new VelocityComponent(velocity);

    return this;
  }


  public CopBuilder movement() {
    this.entity.movementAnimationComponent = new MovementAnimationComponent(UP, DOWN, LEFT, RIGHT);

    return this;
  }


  private boolean isValid() {
    return this.entity.id != null
        && this.entity.positionComponent != null
        && this.entity.directionComponent != null
        && this.entity.velocityComponent != null
        && this.entity.renderComponent != null
        && this.entity.movementAnimationComponent != null;
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


  public Cop build() {
    if (!this.isValid()) {
      throw new IllegalArgumentException();
    }

    this.entity.renderComponent.imageView().setLayoutX(this.entity.positionComponent.x());
    this.entity.renderComponent.imageView().setLayoutY(this.entity.positionComponent.y());

    return this.entity;
  }


  public static class Cop {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private VelocityComponent velocityComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;
    private HealthComponent healthComponent;

    private Cop() {
    }

    public HealthComponent getHealthComponent() {
      return this.healthComponent;
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
