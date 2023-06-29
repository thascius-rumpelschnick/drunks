package org.kappa.client.entity;

import javafx.scene.image.ImageView;
import org.kappa.client.component.PositionComponent;
import org.kappa.client.component.RenderComponent;

public class WaterBuilder {

    private Water entity;

    private WaterBuilder() {
    }

    public static WaterBuilder get() {
        final var builder = new WaterBuilder();
        builder.entity = new Water();

        return builder;
    }

    public WaterBuilder id(final String uuid) {
        this.entity.id = uuid;

        return this;
    }

    public WaterBuilder render(final ImageView imageView) {
        this.entity.renderComponent = new RenderComponent(imageView);

        return this;
    }

    public WaterBuilder position(final int x, final int y) {
        this.entity.positionComponent = new PositionComponent(x, y);

        return this;
    }

    public Water build() {
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
                && this.entity.renderComponent != null;
    }

    public static class Water {

        private String id;
        private PositionComponent positionComponent;
        private RenderComponent renderComponent;
        private Water() {
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
