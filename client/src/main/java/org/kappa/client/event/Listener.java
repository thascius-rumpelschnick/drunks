package org.kappa.client.event;

import org.kappa.client.event.Event;


public interface Listener {

  void onEventReceived(final Event event);

}
