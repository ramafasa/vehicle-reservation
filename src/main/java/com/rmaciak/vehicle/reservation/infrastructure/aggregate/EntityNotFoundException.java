package com.rmaciak.vehicle.reservation.infrastructure.aggregate;

public class EntityNotFoundException extends RuntimeException {

    EntityNotFoundException(String message) {
        super(message);
    }

}
