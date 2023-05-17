package org.kappa.client.game;

import java.util.ArrayList;
import java.util.List;

public final class DrunksObservable {

  private static volatile DrunksObservable observable;
  private List<Object> observers = new ArrayList<>();

  private DrunksObservable() {
    // Must not be invoked directly
  }

  public static DrunksObservable getInstance() {
    if (observable == null) {
      synchronized(DrunksObservable.class) {
        if (observable == null) {
          observable = new DrunksObservable();
        }
      }
    }

    return observable;
  }

}
