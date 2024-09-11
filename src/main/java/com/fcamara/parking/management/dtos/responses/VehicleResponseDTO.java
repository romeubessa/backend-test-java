package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import com.fcamara.parking.management.models.Vehicle;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponseDTO {

    private String id;

    private String brand;

    private String model;

    private String color;

    private String plate;

    private VehicleTypeEnum type;

    public static VehicleResponseDTO toDTO(Vehicle vehicle) {
        return VehicleResponseDTO.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .color(vehicle.getColor())
                .plate(vehicle.getPlate())
                .type(vehicle.getType())
                .build();
    }
}
