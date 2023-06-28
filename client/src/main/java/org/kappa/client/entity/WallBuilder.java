package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.HealthComponent;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;

public class WallBuilder {

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

    public WallBuilder health(final int health) {
        this.entity.healthComponent = new HealthComponent(health);

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

    public Wall build() {
        if (!this.isValid()) {
            throw new IllegalArgumentException();
        }

        return this.entity;
    }

    private boolean isValid() {
        return this.entity.id != null
                && this.entity.positionComponent != null
                && this.entity.renderComponent != null;
    }

    public static class Wall {

        private String id;
        private PositionComponent positionComponent;
        private RenderComponent renderComponent;
        private HealthComponent healthComponent;

        private Wall() {
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

    }
}
