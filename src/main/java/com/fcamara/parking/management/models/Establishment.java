package com.fcamara.parking.management.models;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "establishment")
public class Establishment {

    @NotBlank
    @Indexed(unique = true, background = true)
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Indexed(unique = true)
    private String cnpj;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotNull
    private Integer motorcycleSpots;

    @NotNull
    private Integer carSpots;

    public int getMaxSpotsByVehicleType(VehicleTypeEnum vehicleType) {
        return switch (vehicleType) {
            case CAR -> carSpots;
            case MOTORCYCLE -> motorcycleSpots;
        };
    }
}
