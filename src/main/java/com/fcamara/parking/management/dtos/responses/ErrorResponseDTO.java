package com.fcamara.parking.management.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

    private String message;

    private String code;

    public static ErrorResponseDTO fromDTO(String message, String code) {
        return ErrorResponseDTO.builder()
                .message(message)
                .code(code)
                .build();
    }
}
