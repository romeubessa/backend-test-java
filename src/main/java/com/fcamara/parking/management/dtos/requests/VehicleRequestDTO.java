package com.fcamara.parking.management.dtos.requests;

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
}
