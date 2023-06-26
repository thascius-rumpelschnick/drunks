package org.kappa.client.utils;

import java.util.UUID;


public class IdHelper {

  private IdHelper() {
  }

  public static String createRandomUuid() {
    return UUID.randomUUID().toString();
  }

}
