package com.rmaciak.vehicle.reservation.infrastructure.config;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class EventStoreDBConfig {

    @Bean
    @Scope("singleton")
    EventStoreDBClient eventStoreDBClient(@Value("${esdb.connectionstring}") String connectionString) {
        try {
            return EventStoreDBClient.create(
                    EventStoreDBConnectionString.parse(connectionString)
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
