package org.kappa.client.event;

public abstract class EntityEvent extends Event {
  public EntityEvent(final String entity, final EventType eventType) {
    super(entity, eventType);
  }


  public abstract String getBody();

}
