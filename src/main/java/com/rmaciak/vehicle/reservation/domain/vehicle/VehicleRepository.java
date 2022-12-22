package com.rmaciak.vehicle.reservation.domain.vehicle;

import java.util.function.Consumer;

public interface VehicleRepository {

    Long add(Vehicle vehicle);
    Long update(
            Consumer<Vehicle> updateConsumer,
            long expectedRevision
    );
}
