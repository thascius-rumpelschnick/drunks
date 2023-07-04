package org.kappa.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.kappa.client.ui.DrunksApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DrunksClientApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrunksClientApplication.class);


  @Override
  public void start(final Stage stage) {
    stage.setTitle("Drunks!");
    stage.setResizable(false);

    final var applicationManager = ApplicationManager.getInstance();

    final var httpClient = applicationManager.getHttpClient();
    // LOGGER.debug(httpClient.registerUser("foo", "foobar").name());
    // LOGGER.debug(httpClient.registerUser("foo", "foobar").name());
    //
    // LOGGER.debug(httpClient.getUserData("foo", "foobar").toString());
    // LOGGER.debug(httpClient.saveUserData("foo", "foobar", new UserData("foo", 100, Level.ONE)).toString());
    // LOGGER.debug(httpClient.getUserData("foo", "foobar").toString());

    /*applicationManager.newGame(
        new Game(
            new Player(IdHelper.createRandomUuid(), "user", "password", 100, 0, 5)
            , Level.ONE,
            stage
        )
    );

    stage.show();
    applicationManager.getGame().ifPresent(Game::startGame);*/

    DrunksApplicationRunner.create().runApplication(stage);
  }

  public static void main(final String[] args) {
    launch();
  }
}