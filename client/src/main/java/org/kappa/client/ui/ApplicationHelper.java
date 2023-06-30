package org.kappa.client.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kappa.client.ApplicationManager;
import org.kappa.client.DrunksApplication;
import org.kappa.client.game.Game;
import org.kappa.client.game.Player;
import org.kappa.client.system.SystemManager;
import org.kappa.client.utils.Level;

import java.io.IOException;

public class ApplicationHelper {

    public static final String WELCOME_FXML_FILE = "welcome-view.fxml";
    public static final String LOGIN_FXML_FILE = "login-view.fxml";

    public ApplicationHelper() {
    }

    public static void startMainApplication(Stage stage) {
        final var applicationManager = ApplicationManager.getInstance();
        try {
            applicationManager.newGame(new Game(new Player("player", "player", 0), Level.ONE, stage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        applicationManager.getGame().ifPresent(Game::startGame);
    }

    public static void startApplication(Stage stage) {
        // Load the splash screen FXML file
        FXMLStageLoaderHelper(stage, WELCOME_FXML_FILE);
        stage.show();

        // Set up a timeline to automatically close the screen stage after a specified duration
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DrunksApplication.WELCOME_SCREEN_DURATION), event -> {
            stage.close();

            FXMLStageLoaderHelper(stage, LOGIN_FXML_FILE);
            stage.show();

            Timeline secondTimeline = new Timeline(new KeyFrame(Duration.millis(DrunksApplication.WELCOME_SCREEN_DURATION), secondEvent -> {
                stage.close();

                startMainApplication(stage);
            }));
            secondTimeline.play();
        }));
        timeline.play();
    }

    public static void FXMLStageLoaderHelper(Stage stage, String file) {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(DrunksApplication.class.getResource(file));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

}