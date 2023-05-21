package org.kappa.client.utils;

import java.net.URL;
import org.kappa.client.DrunksApplication;

public class UrlHelper {

  public static URL getRessourceAsUrl(final String url) {
    return DrunksApplication.class.getResource("image/" + url);
  }

  public static String getRessourceAsString(final String url) {
    return getRessourceAsUrl(url).toString();
  }

}
