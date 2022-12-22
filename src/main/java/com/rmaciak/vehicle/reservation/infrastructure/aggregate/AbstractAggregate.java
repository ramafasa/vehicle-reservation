package com.rmaciak.vehicle.reservation.infrastructure.aggregate;

import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractAggregate<Event, Id> implements Aggregate<Id> {
  protected Id id;
  protected int version;

  private final Queue<Object> uncommittedEvents = new LinkedList<>();

  public Id id() {
    return id;
  }

  public Object[] dequeueUncommittedEvents() {
    var dequeuedEvents = uncommittedEvents.toArray();

    uncommittedEvents.clear();

    return dequeuedEvents;
  }

  protected abstract void apply(Event event);

  protected void enqueue(Event event) {
    uncommittedEvents.add(event);
    apply(event);
    version++;
  }
}
