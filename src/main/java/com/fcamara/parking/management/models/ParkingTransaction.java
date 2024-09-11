package com.fcamara.parking.management.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@XmlRootElement
@Data
@Builder
@Document(collection = "parking_transaction")
public class ParkingTransaction {

    @NotBlank
    @Indexed(unique = true, background = true)
    private String id;

    private Establishment establishment;

    private Vehicle vehicle;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;

    public static ParkingTransaction toModel(Establishment establishment, Vehicle vehicle) {
        return ParkingTransaction.builder()
                .establishment(establishment)
                .vehicle(vehicle)
                .entryDate(LocalDateTime.now())
                .build();
    }
}
