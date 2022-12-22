package com.rmaciak.vehicle.reservation.infrastructure.api;


import com.rmaciak.vehicle.reservation.domain.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(("/vehicle"))
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/{vehicleCatalogueId}")
    public Long createNewVehicle(
            @PathVariable("vehicleCatalogueId") UUID vehicleCatalogueId
    ) {
        return vehicleService.create(UUID.randomUUID(), vehicleCatalogueId);
    }

    @PutMapping("/{vehicleId}/publish")
    public Long publishVehicle(
            @PathVariable("vehicleId") UUID vehicleId
    ) {
        return vehicleService.publish(vehicleId, 1L); //fix expected revision
    }

}
