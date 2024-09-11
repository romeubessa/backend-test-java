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
    @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
    private String cnpj;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Phone must follow the pattern (XX) XXXX-XXXX or (XX) XXXXX-XXXX")
    private String phone;

    @NotNull
    private Integer motorcycleSpots;

    @NotNull
    private Integer carSpots;
}
