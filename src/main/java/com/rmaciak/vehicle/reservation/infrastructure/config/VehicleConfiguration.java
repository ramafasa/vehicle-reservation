package com.rmaciak.vehicle.reservation.infrastructure.config;

import com.eventstore.dbclient.EventStoreDBClient;
import com.rmaciak.vehicle.reservation.domain.vehicle.Vehicle;
import com.rmaciak.vehicle.reservation.domain.vehicle.VehicleService;
import com.rmaciak.vehicle.reservation.infrastructure.aggregate.EventStoreAggregateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleConfiguration {

    @Bean
    EventStoreAggregateRepository vehicleRepository(
            EventStoreDBClient eventStoreDBClient
    ) {
        return new EventStoreAggregateRepository<>(
                eventStoreDBClient,
                id -> "VehicleStream--" + id,
                Vehicle::new
        );
    }

    @Bean
    VehicleService vehicleService(EventStoreAggregateRepository vehicleRepository) {
        return new VehicleService(vehicleRepository);
    }
}
