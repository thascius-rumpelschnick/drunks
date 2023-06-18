package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.DirectionComponent;
import org.kappa.client.component.MovementAnimationComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;
import org.kappa.client.utils.Direction;

import java.util.Objects;


public class DrunkBuilder {

  private static final String[] UP = {"boyup_1.png", "boyup_2.png"};
  private static final String[] DOWN = {"boydown_1.png", "boydown_2.png"};
  private static final String[] LEFT = {"boyleft_1.png", "boyleft_2.png"};
  private static final String[] RIGHT = {"boyright_1.png", "boyright_2.png"};

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


  public DrunkBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
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


  public DrunkBuilder movement() {
    this.entity.movementAnimationComponent = new MovementAnimationComponent(UP, DOWN, LEFT, RIGHT);

    return this;
  }


  public Drunk build() {
    return this.entity;
  }


  public static class Drunk {

    private String id;
    private PositionComponent positionComponent;
    private DirectionComponent directionComponent;
    private RenderComponent renderComponent;
    private MovementAnimationComponent movementAnimationComponent;


    private Drunk() {
    }


    public String getId() {
      return this.id;
    }


    public RenderComponent getRenderComponent() {
      return renderComponent;
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
