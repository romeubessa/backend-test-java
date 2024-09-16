package com.fcamara.parking.management.dtos;

import com.fcamara.parking.management.models.Establishment;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EstablishmentDTO {

    private String id;

    private String name;

    private String cnpj;

    private String address;

    private String phone;

    private Integer motorcycleSpots;

    private Integer carSpots;

    public static EstablishmentDTO toDTO(Establishment establishment) {
        return new EstablishmentDTO(establishment.getId(), establishment.getName(), establishment.getCnpj(), establishment.getAddress(), establishment.getPhone(), establishment.getMotorcycleSpots(), establishment.getCarSpots());
    }
}
