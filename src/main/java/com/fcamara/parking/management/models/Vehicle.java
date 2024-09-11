package com.fcamara.parking.management.models;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "vehicle")
public class Vehicle {

    @NotBlank
    @Indexed(unique = true, background = true)
    private String id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    @Indexed(unique = true)
    private String plate;

    @NotBlank
    private VehicleTypeEnum type;
}
