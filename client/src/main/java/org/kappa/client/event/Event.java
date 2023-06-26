package org.kappa.client.event;


public abstract class Event {

  private final String entity;
  private final EventType eventType;


  protected Event(final String entity, final EventType eventType) {
    this.entity = entity;
    this.eventType = eventType;
  }


  public String getEntity() {
    return this.entity;
  }


  public EventType getEventType() {
    return this.eventType;
  }


  public abstract Object getBody();

}
