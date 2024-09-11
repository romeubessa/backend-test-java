package com.fcamara.parking.management.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HourlyParkingSummaryDTO {

    private String hour;

    private long totalEntries;

    private long totalExits;

    public static HourlyParkingSummaryDTO toDTO(String hour, long totalEntries, long totalExits) {
        return new HourlyParkingSummaryDTO(hour, totalEntries, totalExits);
    }
}
