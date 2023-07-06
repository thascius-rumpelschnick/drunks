package org.kappa.client.event;

public class EntityEvent extends Event {
  public EntityEvent(final String entity, final EventType eventType) {
    super(entity, eventType);
  }


  @Override
  public String getBody() {
    return this.getEntity();
  }

}
