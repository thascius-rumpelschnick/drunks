package org.kappa.client.event;

import static org.kappa.client.event.EventType.DAMAGE;


public class DamageEvent extends Event {

  public DamageEvent(final String entity) {
    super(entity, DAMAGE);
  }


  @Override
  public String getBody() {
    return this.getEntity();
  }
}
