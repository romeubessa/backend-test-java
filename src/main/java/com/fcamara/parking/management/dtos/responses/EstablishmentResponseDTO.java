package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.dtos.EstablishmentDTO;
import com.fcamara.parking.management.models.Establishment;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "VehicleResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstablishmentResponseDTO {

    @XmlElementWrapper(name = "establishments")
    @XmlElement(name = "establishment")
    private List<EstablishmentDTO> establishments;

    public static EstablishmentResponseDTO toDTO(List<Establishment> establishments) {
        return new EstablishmentResponseDTO(establishments.stream().map(EstablishmentDTO::toDTO).toList());
    }
}
