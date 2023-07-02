package org.kappa.client.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kappa.client.ApplicationManager;
import org.kappa.client.DrunksClientApplication;
import org.kappa.client.game.Game;
import org.kappa.client.game.Player;
import org.kappa.client.utils.Level;

import java.io.IOException;

public class DrunksApplicationHelper {

    public static final String WELCOME_FXML_FILE = "welcome-view.fxml";
    public static final String WELCOME_PUNK_FXML_FILE = "welcome-punk-view.fxml";
    public static final String LOGIN_FXML_FILE = "login-view.fxml";

    public static void startMainApplication(Stage stage) {
        ApplicationManager applicationManager = ApplicationManager.getInstance();
        try {
            applicationManager.newGame(new Game(new Player("player", "player", "", 0, 0, 0), Level.ONE, stage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        applicationManager.getGame().ifPresent(Game::startGame);

        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                applicationManager.getGame().ifPresent(Game::stopGame);
                stage.close();
                VBox root = loadFXML(stage, "highscore-view.fxml");
                setupHighscoreViewButtons(stage, root);
                stage.show();
            }
        });
    }

    private static void setupHighscoreViewButtons(Stage stage, VBox root) {
        assert root != null;
        Button quitButton = (Button) root.lookup("#quit");
        Button newGameButton = (Button) root.lookup("#newGame");

        quitButton.setOnAction(e -> stage.close());
        newGameButton.setOnAction(e -> startMainApplication(stage));
    }

    public static void startApplication(Stage stage) {

        loadFXMLAndShow(stage, WELCOME_FXML_FILE, DrunksClientApplication.WELCOME_SCREEN_DURATION, () -> loadFXMLAndShow(stage, WELCOME_PUNK_FXML_FILE, DrunksClientApplication.WELCOME_PUNK_SCREEN_DURATION, () -> {
            VBox root = loadFXML(stage, LOGIN_FXML_FILE);
            assert root != null;

            Button playWithoutRegistrationButton = (Button) root.lookup("#PlayWithoutRegistration");
            TextField usernameTextField = (TextField) root.lookup("#usernameTextfield");
            TextField passwordTextField = (TextField) root.lookup("#passwordTextfield");
            Button signUpButton = (Button) root.lookup("#SignUpToPlay");
            stage.show();

            playWithoutRegistrationButton.setOnAction(event -> {
                stage.close();
                startMainApplication(stage);
            });

            signUpButton.setOnAction(event -> {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
            });
        }));
    }

    /*private void updateCopIcon() {
        NonPlayerEntitySystem.getCopCount();
    }*/

    private static void loadFXMLAndShow(Stage stage, String file, int duration, Runnable nextAction) {
        VBox root = loadFXML(stage, file);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> {
            stage.close();
            nextAction.run();
        }));
        timeline.play();
    }

    private static <T extends Parent> T loadFXML(Stage stage, String file) {
        FXMLLoader fxmlLoader = new FXMLLoader(DrunksClientApplication.class.getResource(file));
        try {
            T root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            return root;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file: " + file, e);
        }
    }
}
