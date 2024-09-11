package com.fcamara.parking.management.dtos;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import com.fcamara.parking.management.models.Vehicle;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleDTO {

    private String id;

    private String brand;

    private String model;

    private String color;

    private String plate;

    private VehicleTypeEnum type;

    public static VehicleDTO toDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor(), vehicle.getPlate(), vehicle.getType());
    }
}
