package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.*;
import org.kappa.client.util.Direction;
import org.kappa.client.util.UrlHelper;

import java.util.Objects;


public class CopBuilder {

  private static final String[] UP = {"cop/copup_1.png", "cop/copup_1.png"};
  private static final String[] DOWN = {"cop/copdown_1.png", "cop/copdown_1.png"};
  private static final String[] LEFT = {"cop/copleft_1.png", "cop/copleft_1.png"};
  private static final String[] RIGHT = {"cop/copright_1.png", "cop/copright_1.png"};

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


  public CopBuilder movementAnimation() {
    this.entity.movementAnimationComponent = new MovementAnimationComponent(UP, DOWN, LEFT, RIGHT);

    return this;
  }


  public CopBuilder damageAnimation() {
    this.entity.damageAnimationComponent = new DamageAnimationComponent();

    return this;
  }

  public CopBuilder attack() {
    this.entity.attackComponent = new AttackComponent();

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


  private boolean isValid() {
    return this.entity.id != null
        && this.entity.healthComponent != null
        && this.entity.positionComponent != null
        && this.entity.directionComponent != null
        && this.entity.velocityComponent != null
        && this.entity.renderComponent != null
        && this.entity.damageAnimationComponent != null
        && this.entity.movementAnimationComponent != null
        && this.entity.attackComponent != null;
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
    private HealthComponent healthComponent;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private VelocityComponent velocityComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;
    private DamageAnimationComponent damageAnimationComponent;
    private AttackComponent attackComponent;


    private Cop() {
    }


    public String getId() {
      return this.id;
    }


    public HealthComponent getHealthComponent() {
      return this.healthComponent;
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


    public RenderComponent getRenderComponent() {
      return this.renderComponent;
    }


    public MovementAnimationComponent getMovementAnimationComponent() {
      return this.movementAnimationComponent;
    }


    public DamageAnimationComponent getDamageAnimationComponent() {
      return this.damageAnimationComponent;
    }


    public AttackComponent getAttackComponent() {
      return this.attackComponent;
    }

  }

}
