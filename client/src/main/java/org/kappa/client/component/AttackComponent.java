package org.kappa.client.component;

public class AttackComponent implements Component {

  final private int damage;


  public AttackComponent(final int damage) {
    this.damage = damage;
  }


  public int getDamage() {
    return this.damage;
  }

}
