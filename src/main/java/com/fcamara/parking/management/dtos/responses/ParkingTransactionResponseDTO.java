package com.fcamara.parking.management.dtos.responses;

import com.fcamara.parking.management.adapters.LocalDateTimeAdapter;
import com.fcamara.parking.management.models.ParkingTransaction;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ParkingTransactionResponseDTO {

    private String id;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime entryDate;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime exitDate;

    public static ParkingTransactionResponseDTO toDTO(ParkingTransaction parkingTransaction) {
        return new ParkingTransactionResponseDTO(parkingTransaction.getId(), parkingTransaction.getEntryDate(), parkingTransaction.getExitDate());
    }
}
