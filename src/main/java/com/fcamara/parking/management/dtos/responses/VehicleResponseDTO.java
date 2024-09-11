package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.dtos.VehicleDTO;
import com.fcamara.parking.management.models.Vehicle;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "VehicleResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleResponseDTO {

    @XmlElementWrapper(name = "vehicles")
    @XmlElement(name = "vehicle")
    private List<VehicleDTO> vehicles;

    public static VehicleResponseDTO toDTO(List<Vehicle> vehicles) {
        return new VehicleResponseDTO(vehicles.stream().map(VehicleDTO::toDTO).collect(Collectors.toList()));
    }
}
