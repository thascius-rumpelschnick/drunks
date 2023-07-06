package org.kappa.client.util;

import java.util.UUID;


public enum IdHelper {
  ;


  public static String createRandomUuid() {
    return UUID.randomUUID().toString();
  }

}
