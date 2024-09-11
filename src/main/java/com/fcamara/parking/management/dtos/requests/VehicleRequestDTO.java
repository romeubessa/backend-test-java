package com.fcamara.parking.management.dtos.requests;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import com.fcamara.parking.management.models.Vehicle;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequestDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    private String plate;

    @NotBlank
    private String type;

    public Vehicle toModel() {
        return Vehicle.builder()
                .brand(this.brand)
                .model(this.model)
                .color(this.color)
                .plate(this.plate)
                .type(VehicleTypeEnum.getVehicleType(this.type))
                .build();
    }
}
