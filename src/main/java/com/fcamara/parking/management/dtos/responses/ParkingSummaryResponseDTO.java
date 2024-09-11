package com.fcamara.parking.management.dtos.responses;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ParkingSummaryResponse")
public class ParkingSummaryResponseDTO {

    private long totalEntries;

    private long totalExits;

    public static ParkingSummaryResponseDTO toDTO(long totalEntries, long totalExits) {
        return new ParkingSummaryResponseDTO(totalEntries, totalExits);
    }
}
