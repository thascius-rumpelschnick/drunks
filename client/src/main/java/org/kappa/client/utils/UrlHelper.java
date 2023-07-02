package org.kappa.client.utils;

import org.kappa.client.DrunksClientApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;


public class UrlHelper {
  private final static Logger LOGGER = LoggerFactory.getLogger(UrlHelper.class);

  public static URL getRessourceAsUrl(final String url) {
    return DrunksClientApplication.class.getResource("images/" + url);
  }


  public static String getRessourceAsString(final String url) {
    try {
      return getRessourceAsUrl(url).toString();
    } catch (Exception e) {
      LOGGER.error(url);
    }
    return getRessourceAsUrl(url).toString();
  }
}
