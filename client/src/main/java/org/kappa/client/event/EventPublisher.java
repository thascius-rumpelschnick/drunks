package org.kappa.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EventPublisher implements Publisher {

  private static volatile EventPublisher publisher;
  private final Map<EventType, List<Listener>> listeners = new HashMap<>();


  private EventPublisher() {
    // Must not be invoked directly
  }


  public static EventPublisher getInstance() {
    if (publisher == null) {
      synchronized (EventPublisher.class) {
        if (publisher == null) {
          publisher = new EventPublisher();
        }
      }
    }

    return publisher;
  }


  public void publishEvent(final Event event) {
    final var eventType = event.getEventType();

    if (!this.listeners.containsKey(eventType)) {
      return;
    }

    for (final Listener listener : this.listeners.get(eventType)) {
      listener.onEventReceived(event);
    }
  }


  @Override
  public void subscribe(final EventType eventType, final Listener listener) {
    if (!this.listeners.containsKey(eventType)) {
      this.listeners.put(eventType, new ArrayList<>());
    }

    this.listeners.get(eventType).add(listener);
  }


  @Override
  public void unsubscribe(final EventType eventType, final Listener listener) {
    if (!this.listeners.containsKey(eventType)) {
      return;
    }

    this.listeners.get(eventType).remove(listener);
  }


  public void reset() {
    this.listeners.clear();
  }
}
