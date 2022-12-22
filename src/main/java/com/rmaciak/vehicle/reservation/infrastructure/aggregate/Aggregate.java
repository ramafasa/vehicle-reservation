package com.rmaciak.vehicle.reservation.infrastructure.aggregate;

public interface Aggregate<Id> {
  Id id();
  Object[] dequeueUncommittedEvents();
}
