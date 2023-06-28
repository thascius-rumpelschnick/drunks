package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.DamageAnimationComponent;
import org.kappa.client.component.HealthComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;


public class WallBuilder {

  private static final String[] SPRITES = {
      "enemy/copup_1.png",
      "enemy/copdown_1.png",
      "enemy/copleft_1.png",
      "enemy/copright_1.png"
  };

  private Wall entity;


  private WallBuilder() {
  }


  public static WallBuilder get() {
    final var builder = new WallBuilder();
    builder.entity = new Wall();

    return builder;
  }


  public WallBuilder id(final String uuid) {
    this.entity.id = uuid;

    return this;
  }


  public WallBuilder render(final ImageView imageView) {
    this.entity.renderComponent = new RenderComponent(imageView);

    return this;
  }


  public WallBuilder position(final int x, final int y) {
    this.entity.positionComponent = new PositionComponent(x, y);

    return this;
  }


  public WallBuilder health(final int health) {
    this.entity.healthComponent = new HealthComponent(health);

    return this;
  }


  public WallBuilder damage() {
    this.entity.damageAnimationComponent = new DamageAnimationComponent();

    return this;
  }


  public Wall build() {
    if (!this.isValid()) {
      throw new IllegalArgumentException();
    }

    return this.entity;
  }


  private boolean isValid() {
    return this.entity.id != null
        && this.entity.positionComponent != null
        && this.entity.renderComponent != null
        && this.entity.healthComponent != null
        && this.entity.damageAnimationComponent != null;
  }


  public static class Wall {

    private String id;
    private PositionComponent positionComponent;
    private RenderComponent renderComponent;
    private HealthComponent healthComponent;

    private DamageAnimationComponent damageAnimationComponent;


    private Wall() {
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


    public HealthComponent getHealthComponent() {
      return this.healthComponent;
    }


    public DamageAnimationComponent getDamageAnimationComponent() {
      return this.damageAnimationComponent;
    }
  }
}
