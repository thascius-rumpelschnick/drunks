package org.kappa.client.system;

import org.kappa.client.entity.EntityManager;
import org.kappa.client.event.Event;
import org.kappa.client.event.Listener;


public class Movement implements Listener {

  private final EntityManager entityManager;


  public Movement(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public void onEventReceived(Event event) {

  }
}
