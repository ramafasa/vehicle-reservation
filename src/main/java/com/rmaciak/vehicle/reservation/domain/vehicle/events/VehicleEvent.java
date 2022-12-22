package com.rmaciak.vehicle.reservation.domain.vehicle.events;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public sealed interface VehicleEvent {

    record VehicleCreated(
            UUID vehicleId,
            UUID vehicleCatalogueId
    ) implements VehicleEvent {

    }

    record VehiclePublished(
            UUID vehicleId
    ) implements VehicleEvent {

    }

    record VehicleUnpublished(
            UUID vehicleId
    ) implements VehicleEvent {

    }

    record VehicleBooked(
            UUID reservationId,
            UUID clientId,
            UUID vehicleId,
            Instant from,
            Duration duration,
            BigDecimal price
    ) implements  VehicleEvent {

    }

}
