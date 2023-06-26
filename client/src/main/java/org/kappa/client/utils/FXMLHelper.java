package org.kappa.client.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.kappa.client.DrunksApplication;

public class FXMLHelper {

  private FXMLHelper() {
    // Must not be initialised.
  }

  public static Node createNodeFromFXML(final String fxmlFile) throws IOException {
    return new FXMLLoader(DrunksApplication.class.getResource(fxmlFile)).load();
  }

  public static Scene createSceneFromFXML(final String fxmlFile) throws IOException {
    return new FXMLLoader(DrunksApplication.class.getResource(fxmlFile)).load();
  }

}
