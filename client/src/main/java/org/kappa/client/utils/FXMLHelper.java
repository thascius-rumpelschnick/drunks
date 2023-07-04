package org.kappa.client.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.kappa.client.DrunksClientApplication;

import java.io.IOException;


public enum FXMLHelper {
  ;

  public static Parent createParentFromFXML(final String fxmlFile) throws IOException {
    return new FXMLLoader(DrunksClientApplication.class.getResource(fxmlFile)).load();
  }

  public static Scene createSceneFromFXML(final String fxmlFile) throws IOException {
    return new FXMLLoader(DrunksClientApplication.class.getResource(fxmlFile)).load();
  }

  public static void validateLayoutPosition(final double x, final double y) {
    if (x % LayoutValues.GAMEBOARD_TILE != 0) {
      throw new IllegalArgumentException("[X] not on grid: " + x);
    }

    if (y % LayoutValues.GAMEBOARD_TILE != 0) {
      throw new IllegalArgumentException("[Y] not on grid: " + y);
    }
  }

}
