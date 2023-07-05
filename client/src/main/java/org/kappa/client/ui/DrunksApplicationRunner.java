package org.kappa.client.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kappa.client.ApplicationManager;
import org.kappa.client.game.Game;
import org.kappa.client.game.GameStats;
import org.kappa.client.game.Player;
import org.kappa.client.http.Status;
import org.kappa.client.http.UserData;
import org.kappa.client.utils.FXMLHelper;
import org.kappa.client.utils.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.kappa.client.http.Status.*;


public class DrunksApplicationRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrunksApplicationRunner.class);

  private static final String WELCOME_FXML_FILE = "welcome-view.fxml";
  private static final String WELCOME_PUNK_FXML_FILE = "welcome-punk-view.fxml";
  private static final String LOGIN_FXML_FILE = "login-view.fxml";

  private static final int WELCOME_SCREEN_DURATION = 500;
  private static final int WELCOME_PUNK_SCREEN_DURATION = 250;

  private final ApplicationManager applicationManager = ApplicationManager.getInstance();


  public void startMainApplication(final Stage stage, final Player player, final GameStats gameStats) {
    try {
      this.applicationManager.newGame(new Game(player, gameStats, stage));
    } catch (final IOException exception) {
      LOGGER.error(exception.getMessage(), exception);
      System.exit(1);
    }

    stage.show();
    this.applicationManager.getGame().ifPresent(Game::startGame);

    stage.getScene().setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        this.applicationManager.getGame().ifPresent(Game::stopGame);
        stage.close();

        final VBox root = (VBox) this.loadRootAndSetScene(stage, "highscore-view.fxml");
        final Button quitButton = (Button) root.lookup("#quit");
        final Button newGameButton = (Button) root.lookup("#newGame");

        quitButton.setOnAction(e -> stage.close());
        newGameButton.setOnAction(e -> this.startMainApplication(stage, player, gameStats));
        stage.show();
      }
    });
  }


  public void run(final Stage stage) {
    // Show Drunks Logo
    // loadRootAndSetScene(stage, WELCOME_FXML_FILE);
    // stage.show();

    // Show Drunks Logo for a while
    // Show Punk Logo for a while
    // Then show form
    this.loadFXMLAndShow(
        stage,
        WELCOME_FXML_FILE,
        WELCOME_SCREEN_DURATION,
        () -> this.loadFXMLAndShow(
            stage,
            WELCOME_PUNK_FXML_FILE,
            WELCOME_PUNK_SCREEN_DURATION,
            () -> this.handleFormLogic(stage)
        )
    );
  }


  private void handleFormLogic(final Stage stage) {
    final VBox root = (VBox) this.loadRootAndSetScene(stage, LOGIN_FXML_FILE);
    stage.show();

    final var httpClient = this.applicationManager.getHttpClient();

    final TextField username = (TextField) root.lookup("#username");
    final TextField password = (TextField) root.lookup("#password");

    final Text usernameText = (Text) root.lookup("#username-txt");
    final Text passwordText = (Text) root.lookup("#password-txt");

    final Button login = (Button) root.lookup("#login");
    login.setOnAction(event -> {
      final var status = httpClient.getUser(username.getText(), password.getText());

      if (DENIED == status) {
        final var message = "Register user";
        usernameText.setText(message);
        passwordText.setText(message);
      }

      if (ACCEPTED == status) {
        final var userData = httpClient.getUserData(username.getText(), password.getText());

        final var player = new Player(
            username.getText(),
            username.getText(),
            password.getText(),
            userData.map(UserData::highScore).orElse(0)
        );

        final var gameStats = new GameStats(
            userData.map(UserData::level).orElse(Level.ONE),
            5,
            0
        );

        stage.close();
        this.startMainApplication(stage, player, gameStats);
      }
    });

    final Button register = (Button) root.lookup("#register");
    register.setOnAction(event -> {
      final Status status;
      try {
        status = httpClient.registerUser(username.getText(), password.getText());
      } catch (final JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      if (INTERNAL_SERVER_ERROR == status) {
        usernameText.setText("Not empty and not more than 15 characters");
        passwordText.setText("Minimum 5 characters");
      }

      if (BAD_REQUEST == status) {
        final var message = "User already registered";
        usernameText.setText(message);
        passwordText.setText(message);
      }

      if (ACCEPTED == status) {
        final var player = new Player(
            username.getText(),
            username.getText(),
            password.getText(),
            0
        );

        final var gameStats = createDummyStats();

        stage.close();
        this.startMainApplication(stage, player, gameStats);
      }
    });

    final Button playWithoutRegistrationButton = (Button) root.lookup("#play-without-registration");
    playWithoutRegistrationButton.setOnAction(event -> {
      final var player = createDummyPlayer();
      final var gameStats = createDummyStats();

      stage.close();
      this.startMainApplication(stage, player, gameStats);
    });
  }


  private void loadFXMLAndShow(final Stage stage, final String file, final int duration, final Runnable nextAction) {
    this.loadRootAndSetScene(stage, file);
    stage.show();

    final var timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> {
      stage.close();
      nextAction.run();
    }));

    timeline.play();
  }


  private Parent loadRootAndSetScene(final Stage stage, final String file) {
    try {
      final var root = FXMLHelper.createParentFromFXML(file);
      final var scene = new Scene(root);
      stage.setScene(scene);

      return root;
    } catch (final IOException exception) {
      LOGGER.error(exception.getMessage(), exception);

      System.exit(1);
    }

    return null;
  }


  private static Player createDummyPlayer() {
    return new Player("player", "player", "", 0);
  }


  private static GameStats createDummyStats() {
    return new GameStats(Level.ONE, 5, 0);
  }


  public static DrunksApplicationRunner create() {
    return new DrunksApplicationRunner();
  }

}
