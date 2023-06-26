package org.kappa.client.event;

public interface Listener<E extends Event> {

  void updateOnEventReceived(final E event);

}
