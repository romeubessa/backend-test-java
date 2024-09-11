package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.models.ParkingTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VehicleMovementReportResponseDTO {

    private String vehiclePlate;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;

    public static VehicleMovementReportResponseDTO toDTO(ParkingTransaction parkingTransaction) {
        return VehicleMovementReportResponseDTO.builder()
                .vehiclePlate(parkingTransaction.getVehicle().getPlate())
                .entryDate(parkingTransaction.getEntryDate())
                .exitDate(parkingTransaction.getExitDate())
                .build();
    }
}
