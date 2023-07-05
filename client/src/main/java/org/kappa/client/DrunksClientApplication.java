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
    DrunksApplicationRunner.create().run(stage);
  }


  public static void main(final String[] args) {
    launch();
  }

}