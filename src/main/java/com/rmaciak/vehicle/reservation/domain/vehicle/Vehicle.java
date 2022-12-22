package com.rmaciak.vehicle.reservation.domain.vehicle;

import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent;
import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent.VehicleBooked;
import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent.VehicleCreated;
import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent.VehiclePublished;
import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent.VehicleUnpublished;
import com.rmaciak.vehicle.reservation.infrastructure.aggregate.AbstractAggregate;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static com.rmaciak.vehicle.reservation.domain.vehicle.Status.NEW;
import static com.rmaciak.vehicle.reservation.domain.vehicle.Status.PUBLISHED;

public final class Vehicle extends AbstractAggregate<VehicleEvent, UUID> {

    private UUID vehicleId;
    private UUID vehicleCatalogueId;
    private Status status;
    private Set<Booking> bookings;

    public Vehicle() {
    }

    private Vehicle(UUID vehicleId, UUID vehicleCatalogueId) {
        this.vehicleId = vehicleId;
        this.vehicleCatalogueId = vehicleCatalogueId;
        this.status = NEW;
        this.bookings = Collections.emptySet();
        enqueue(new VehicleCreated(vehicleId, vehicleCatalogueId));
    }

    public static Vehicle create(UUID vehicleId, UUID vehicleCatalogueId) {
        return new Vehicle(vehicleId, vehicleCatalogueId);
    }

    public Vehicle publish() {
        if (status != NEW) {
            throw new IllegalStateException("Vehicle must be NEW to be published");
        }

        enqueue(new VehiclePublished(vehicleId));
        return this;
    }

    public Vehicle unpublish() {
        if (status != PUBLISHED) {
            throw new IllegalStateException("Vehicle must be PUBLISHED to be unpublished");
        }

        enqueue(new VehicleUnpublished(vehicleId));
        return this;
    }

    public Vehicle book(UUID clientId, Instant from, Duration duration, BigDecimal price) {
        if (status != PUBLISHED) {
            throw new IllegalStateException("Vehicle must be PUBLISHED to be booked");
        }

        //validate whether another booking exists

        enqueue(new VehicleBooked(UUID.randomUUID(), clientId, vehicleId, from, duration, price));
        return this;
    }

    @Override
    protected void apply(VehicleEvent vehicleEvent) {
        switch (vehicleEvent) {
            case VehicleEvent.VehicleCreated vehicleCreated -> {
                vehicleId = vehicleCreated.vehicleId();
                vehicleCatalogueId = vehicleCreated.vehicleCatalogueId();
                status = NEW;
            }
            case VehiclePublished vehiclePublished -> {
                status = PUBLISHED;
            }
            case VehicleBooked vehicleBooked -> {
                this.bookings.add(
                        new Booking(
                                vehicleBooked.from(),
                                vehicleBooked.duration(),
                                vehicleBooked.price(),
                                vehicleBooked.clientId()
                        )
                );
            }
            case VehicleUnpublished vehicleUnpublished -> {
                status = NEW;
            }
        }
    }

    @AllArgsConstructor
    class Booking {
        private Instant from;
        private Duration duration;
        private BigDecimal price;
        private UUID clientId;
    }
}

enum Status {
    NEW,
    PUBLISHED
}
