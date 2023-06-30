package org.kappa.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.kappa.client.ui.DrunksApplicationHelper;

public class DrunksApplication extends Application {

  public static final int WELCOME_SCREEN_DURATION = 5000;

  @Override
  public void start(final Stage stage) {
    stage.setTitle("Drunks!");
    stage.setResizable(false);

    DrunksApplicationHelper.startApplication(stage);
  }

  public static void main(final String[] args) {
    launch();
  }
}