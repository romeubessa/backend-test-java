package com.fcamara.parking.management.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingSummaryResponseDTO {

    private long totalEntries;

    private long totalExits;

    public static ParkingSummaryResponseDTO toDTO(long totalEntries, long totalExits) {
        return ParkingSummaryResponseDTO.builder()
                .totalEntries(totalEntries)
                .totalExits(totalExits)
                .build();
    }
}
