package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.dtos.VehicleMovementReportDTO;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleMovementReportResponseDTO {

    @XmlElementWrapper(name = "movements")
    @XmlElement(name = "movement")
    private List<VehicleMovementReportDTO> movements;

    public static VehicleMovementReportResponseDTO toDTO(List<VehicleMovementReportDTO> movements) {
        return new VehicleMovementReportResponseDTO(movements);
    }
}
