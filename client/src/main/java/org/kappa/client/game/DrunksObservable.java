package org.kappa.client.game;

import java.util.ArrayList;
import java.util.List;

public final class DrunksObservable implements Observable {

  private static volatile DrunksObservable observable;
  private List<Observer> observers = new ArrayList<>();

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

  public void update(final int notification) {
    for (final Observer observer: this.observers) {
      observer.update(notification);
    }
  }

  @Override
  public void register(final Observer observer) {
    this.observers.add(observer);
  }

  @Override
  public void unregister(final Observer observer) {
    this.observers.remove(observer);
  }
}
