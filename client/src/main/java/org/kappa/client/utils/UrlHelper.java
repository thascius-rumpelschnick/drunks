package org.kappa.client.utils;

import org.kappa.client.DrunksClientApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


public enum UrlHelper {
  ;

  private static final Logger LOGGER = LoggerFactory.getLogger(UrlHelper.class);


  public static URL getRessourceAsUrl(final String url) {
    return DrunksClientApplication.class.getResource("images/" + url);
  }


  public static String getRessourceAsString(final String url) {
    return getRessourceAsUrl(url).toString();
  }

}
