package org.kappa.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.kappa.client.ui.ApplicationHelper;

public class DrunksApplication extends Application {

  public static final int WELCOME_SCREEN_DURATION = 5000;

  @Override
  public void start(final Stage stage) {
    stage.setTitle("Drunks!");
    stage.setResizable(false);

    ApplicationHelper.startApplication(stage);
  }

  public static void main(final String[] args) {
    launch();
  }
}