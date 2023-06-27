package org.kappa.client.utils;

import org.kappa.client.DrunksApplication;

import java.io.File;
import java.net.URL;


public enum UrlHelper {
  ;


  public static URL getRessourceAsUrl(final String url) {
    return DrunksApplication.class.getResource("images" + File.separator + url);
  }


  public static String getRessourceAsString(final String url) {
    return getRessourceAsUrl(url).toString();
  }

}
