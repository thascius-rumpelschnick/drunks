package org.kappa.client.event;

import org.kappa.client.utils.AttackType;

import static org.kappa.client.event.EventType.ATTACK;


public class AttackEvent extends Event {

  private final AttackType attackType;


  public AttackEvent(final String entity, final AttackType attackType) {
    super(entity, ATTACK);
    this.attackType = attackType;
  }


  @Override
  public AttackType getBody() {
    return this.attackType;
  }

}
