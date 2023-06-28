package org.kappa.client.event;


public interface Publisher {

  void publishEvent(final Event event);

  void subscribe(final EventType eventType, final Listener listener);

  void unsubscribe(final EventType eventType, final Listener listener);

}
