package org.kappa.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.kappa.client.http.UserData;
import org.kappa.client.ui.DrunksApplicationHelper;
import javafx.util.Duration;
import org.kappa.client.game.Game;
import org.kappa.client.game.Player;
import org.kappa.client.utils.IdHelper;
import org.kappa.client.utils.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class DrunksClientApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrunksClientApplication.class);

  public static final int WELCOME_SCREEN_DURATION = 5000;
  public static final int WELCOME_PUNK_SCREEN_DURATION = 2500;

  @Override
  public void start(final Stage stage) throws JsonProcessingException {
    stage.setTitle("Drunks!");
    stage.setResizable(false);

    final var applicationManager = ApplicationManager.getInstance();

    final var httpClient = applicationManager.getHttpClient();
    //LOGGER.debug(httpClient.registerUser("foo", "foobar").name());
    //enum compareTo Equals oder status 200 prüfen
    //LOGGER.debug(httpClient.registerUser("foo", "foobar").name());
    //LOGGER.debug(httpClient.getUserData("foo", "foobar").toString());
    //LOGGER.debug(httpClient.saveUserData("foo", "foobar", new UserData("foo", 100, Level.ONE)).toString());
    //LOGGER.debug(httpClient.getUserData("foo", "foobar").toString());

    /*applicationManager.newGame(
        new Game(
            new Player(IdHelper.createRandomUuid(), "user", "password", 100, 0, 5)
            , Level.ONE,
            stage
        )
    );

    stage.show();
    applicationManager.getGame().ifPresent(Game::startGame);*/

    DrunksApplicationHelper.startApplication(stage);
  }

  public static void main(final String[] args) {
    launch();
  }
}