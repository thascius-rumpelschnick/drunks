package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.utils.Direction;

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


  public CopBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
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


  public CopBuilder movement() {
    this.entity.movementAnimationComponent = new MovementAnimationComponent(UP, DOWN, LEFT, RIGHT);

    return this;
  }


  public Cop build() {
    return this.entity;
  }


  public static class Cop {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;


    private Cop() {
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
