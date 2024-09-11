package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.models.Establishment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstablishmentResponseDTO {

    private String id;

    private String name;

    private String address;

    private String phone;

    private Integer motorcycleSpots;

    private Integer carSpots;

    public static EstablishmentResponseDTO toDTO(Establishment establishment) {
        return EstablishmentResponseDTO.builder()
                .id(establishment.getId())
                .name(establishment.getName())
                .address(establishment.getAddress())
                .phone(establishment.getPhone())
                .motorcycleSpots(establishment.getMotorcycleSpots())
                .carSpots(establishment.getCarSpots())
                .build();
    }
}
