package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.models.ParkingTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParkingTransactionResponseDTO {

    private String id;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;

    public static ParkingTransactionResponseDTO toDTO(ParkingTransaction parkingTransaction) {
        return ParkingTransactionResponseDTO.builder()
                .id(parkingTransaction.getId())
                .entryDate(parkingTransaction.getEntryDate())
                .exitDate(parkingTransaction.getExitDate())
                .build();
    }
}
