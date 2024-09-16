package com.fcamara.parking.management.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EstablishmentRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotNull
    private Integer motorcycleSpots;

    @NotNull
    private Integer carSpots;
}
