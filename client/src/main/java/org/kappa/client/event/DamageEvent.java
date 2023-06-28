package org.kappa.client.event;

import static org.kappa.client.event.EventType.DAMAGE;


public class DamageEvent extends Event {

  private final int damage;


  public DamageEvent(final String entity, final int damage) {
    super(entity, DAMAGE);
    this.damage = damage;
  }


  @Override
  public Integer getBody() {
    return this.damage;
  }
}
