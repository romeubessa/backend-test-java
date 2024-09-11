package com.fcamara.parking.management.models;

import com.fcamara.parking.management.dtos.requests.EstablishmentRequestDTO;
import com.fcamara.parking.management.enums.VehicleTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static Establishment toModel(EstablishmentRequestDTO establishmentRequestDTO) {
        return Establishment.builder()
                .name(establishmentRequestDTO.getName())
                .cnpj(establishmentRequestDTO.getCnpj())
                .address(establishmentRequestDTO.getAddress())
                .phone(establishmentRequestDTO.getPhone())
                .motorcycleSpots(establishmentRequestDTO.getMotorcycleSpots())
                .carSpots(establishmentRequestDTO.getCarSpots())
                .build();
    }
}
