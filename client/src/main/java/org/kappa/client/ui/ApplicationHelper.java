package org.kappa.client.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kappa.client.ApplicationManager;
import org.kappa.client.DrunksApplication;
import org.kappa.client.game.Game;
import org.kappa.client.game.Player;
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
        FXMLStageLoaderHelper(stage, WELCOME_FXML_FILE);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DrunksApplication.WELCOME_SCREEN_DURATION), event -> {
            stage.close();

            VBox root = FXMLStageLoaderHelper(stage, LOGIN_FXML_FILE);

            Button playWithoutRegistrationButton = (Button) root.lookup("#PlayWithoutRegistration");
            stage.show();

            playWithoutRegistrationButton.setOnAction(secondEvent -> {
                stage.close();

                startMainApplication(stage);
            });
        }));
        timeline.play();
    }

    public static <T extends Parent> T FXMLStageLoaderHelper(Stage stage, String file) {
        FXMLLoader fxmlLoader = new FXMLLoader(DrunksApplication.class.getResource(file));
        try {
            T root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}