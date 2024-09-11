package com.fcamara.parking.management.dtos.responses;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ErrorResponse")
public class ErrorResponseDTO {

    private String message;

    private String code;

    public static ErrorResponseDTO fromDTO(String message, String code) {
        return new ErrorResponseDTO(message, code);
    }
}
