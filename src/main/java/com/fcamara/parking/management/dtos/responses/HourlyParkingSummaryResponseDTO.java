package com.fcamara.parking.management.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HourlyParkingSummaryResponseDTO {

    private String hour;

    private long totalEntries;

    private long totalExits;

    public static HourlyParkingSummaryResponseDTO toDTO(String hour, long totalEntries, long totalExits) {
        return HourlyParkingSummaryResponseDTO.builder()
                .hour(hour)
                .totalEntries(totalEntries)
                .totalExits(totalExits)
                .build();
    }
}
