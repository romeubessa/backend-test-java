package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.dtos.HourlyParkingSummaryDTO;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HourlyParkingSummaryResponseDTO {

    @XmlElementWrapper(name = "summaries")
    @XmlElement(name = "summary")
    private List<HourlyParkingSummaryDTO> summaries;

    public static HourlyParkingSummaryResponseDTO toDTO(List<HourlyParkingSummaryDTO> summaries) {
        return new HourlyParkingSummaryResponseDTO(summaries);
    }
}
