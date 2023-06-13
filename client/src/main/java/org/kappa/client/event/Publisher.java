package org.kappa.client.event;

import org.kappa.client.event.Event;
import org.kappa.client.event.EventType;
import org.kappa.client.event.Listener;


public interface Publisher {

  void publishEvent(final Event event);

  void subscribe(final EventType eventType, final Listener listener);

  void unsubscribe(final EventType eventType, final Listener listener);

}
