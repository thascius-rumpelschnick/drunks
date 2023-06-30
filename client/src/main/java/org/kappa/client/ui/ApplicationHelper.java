package org.kappa.client.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.kappa.client.system.RenderSystem;
import org.kappa.client.system.SystemManager;

public class ApplicationHelper {

    private final SystemManager systemManager;

    public static final String WELCOME_FXML_FILE = "welcome-view.fxml";

    public ApplicationHelper() {
        this.systemManager = new SystemManager();
    }

    public Timeline fadeScreen(Pane board, Scene scene) {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> this.systemManager.getSystem(RenderSystem.class).setGameView(new GameView(new BoardView(board), scene))));
        timeline.play();
        return timeline;
    }
}













