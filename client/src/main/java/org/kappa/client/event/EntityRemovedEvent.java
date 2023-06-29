package org.kappa.client.event;

import static org.kappa.client.event.EventType.ENTITY_REMOVED;


public class EntityRemovedEvent extends EntityEvent {
  public EntityRemovedEvent(final String entity) {
    super(entity, ENTITY_REMOVED);
  }


  @Override
  public String getBody() {
    return this.getEntity();
  }

}
