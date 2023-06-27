package org.kappa.client.event;

import static org.kappa.client.event.EventType.ENTITY_CREATED;


public class EntityCreatedEvent extends Event {

  public EntityCreatedEvent(final String entity) {
    super(entity, ENTITY_CREATED);
  }


  @Override
  public String getBody() {
    return this.getEntity();
  }

}
