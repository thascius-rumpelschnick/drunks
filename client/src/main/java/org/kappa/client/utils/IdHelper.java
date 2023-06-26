package org.kappa.client.utils;

import java.util.UUID;


public enum IdHelper {
  ;


  public static String createRandomUuid() {
    return UUID.randomUUID().toString();
  }

}
