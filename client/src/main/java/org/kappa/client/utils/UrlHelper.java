package org.kappa.client.utils;

import org.kappa.client.DrunksApplication;

import java.net.URL;

public enum UrlHelper {
  ;


  public static URL getRessourceAsUrl(final String url) {
    return DrunksApplication.class.getResource("images/" + url);
  }

  public static String getRessourceAsString(final String url) {
    return getRessourceAsUrl(url).toString();
  }

}
