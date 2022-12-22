package com.rmaciak.vehicle.reservation.domain.vehicle;

import com.rmaciak.vehicle.reservation.domain.vehicle.events.VehicleEvent;
import com.rmaciak.vehicle.reservation.infrastructure.aggregate.EventStoreAggregateRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
public class VehicleService {

    private final EventStoreAggregateRepository<Vehicle, VehicleEvent, UUID> vehicleRepository;

    public Long create(UUID vehicleId, UUID vehicleCatalogueId) {
        return vehicleRepository.add(Vehicle.create(vehicleId, vehicleCatalogueId));
    }

    public Long publish(UUID vehicleId, Long expectedVersion) {
        return vehicleRepository.getAndUpdate(
                vehicle -> vehicle.publish(),
                vehicleId,
                expectedVersion
        );
    }

    public Long unpublish(UUID vehicleId, Long expectedVersion) {
        return vehicleRepository.getAndUpdate(
                vehicle -> vehicle.unpublish(),
                vehicleId,
                expectedVersion
        );
    }

    public Long bookVehicle(UUID vehicleId, Long expectedVersion, UUID clientId, Instant from, Duration duration,
                           BigDecimal price) {
        return vehicleRepository.getAndUpdate(
                vehicle -> vehicle.book(clientId, from, duration, price),
                vehicleId,
                expectedVersion
        );
    }


}
