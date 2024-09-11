package com.fcamara.parking.management.services;

import com.fcamara.parking.management.dtos.HourlyParkingSummaryDTO;
import com.fcamara.parking.management.dtos.VehicleMovementReportDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.models.ParkingTransaction;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.repositorys.ParkingTransactionRepository;
import com.fcamara.parking.management.services.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportServiceTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ParkingTransactionRepository parkingTransactionRepository;

    @Mock
    private EstablishmentService establishmentService;

    @Test
    void getSummary_Success() {
        String establishmentId = "1";
        when(establishmentService.getById(establishmentId)).thenReturn(any());
        when(parkingTransactionRepository.countByEntryDateIsNotNullAndEstablishmentId(establishmentId)).thenReturn(10L);
        when(parkingTransactionRepository.countByExitDateIsNotNullAndEstablishmentId(establishmentId)).thenReturn(8L);

        ParkingSummaryResponseDTO summary = reportService.getSummary(establishmentId);

        assertNotNull(summary);

        assertEquals(10, summary.getTotalEntries());

        assertEquals(8, summary.getTotalExits());

        verify(establishmentService, times(1)).getById(establishmentId);

        verify(parkingTransactionRepository, times(1)).countByEntryDateIsNotNullAndEstablishmentId(establishmentId);

        verify(parkingTransactionRepository, times(1)).countByExitDateIsNotNullAndEstablishmentId(establishmentId);
    }

    @Test
    void getHourlySummary_Success() {
        String establishmentId = "1";

        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        when(establishmentService.getById(establishmentId)).thenReturn(establishment);

        when(parkingTransactionRepository.countByEntryDateBetweenAndEstablishmentId(any(LocalDateTime.class), any(LocalDateTime.class), eq(establishmentId)))
                .thenReturn(5L);

        when(parkingTransactionRepository.countByExitDateBetweenAndEstablishmentId(any(LocalDateTime.class), any(LocalDateTime.class), eq(establishmentId)))
                .thenReturn(3L);

        List<HourlyParkingSummaryDTO> hourlySummary = reportService.getHourlySummary(establishmentId);

        assertNotNull(hourlySummary);

        assertEquals(24, hourlySummary.size());

        assertEquals(5, hourlySummary.get(0).getTotalEntries());

        assertEquals(3, hourlySummary.get(0).getTotalExits());

        verify(establishmentService, times(1)).getById(establishmentId);

        verify(parkingTransactionRepository, times(24)).countByEntryDateBetweenAndEstablishmentId(any(LocalDateTime.class), any(LocalDateTime.class), eq(establishmentId));

        verify(parkingTransactionRepository, times(24)).countByExitDateBetweenAndEstablishmentId(any(LocalDateTime.class), any(LocalDateTime.class), eq(establishmentId));
    }

    @Test
    void getVehicleMovementReport_Success() {
        String establishmentId = "1";
        String startDate = "2024-09-10";
        String endDate = "2024-09-11";

        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        when(establishmentService.getById(eq(establishmentId))).thenReturn(establishment);

        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC1234");

        ParkingTransaction parkingTransaction = new ParkingTransaction();
        parkingTransaction.setId("1");
        parkingTransaction.setVehicle(vehicle);
        parkingTransaction.setEntryDate(LocalDateTime.of(2024, 9, 10, 8, 0));
        parkingTransaction.setExitDate(LocalDateTime.of(2024, 9, 10, 10, 0));

        when(parkingTransactionRepository.findAllByEstablishmentIdAndEntryDateBetween(
                eq(establishmentId),
                any(LocalDateTime.class),
                any(LocalDateTime.class))
        ).thenReturn(Collections.singletonList(parkingTransaction));

        List<VehicleMovementReportDTO> vehicleMovementReport = reportService.getVehicleMovementReport(establishmentId, startDate, endDate);

        assertNotNull(vehicleMovementReport);

        assertEquals(1, vehicleMovementReport.size());

        assertEquals("ABC1234", vehicleMovementReport.get(0).getVehiclePlate());

        verify(establishmentService, times(1)).getById(eq(establishmentId));

        verify(parkingTransactionRepository, times(1)).findAllByEstablishmentIdAndEntryDateBetween(
                eq(establishmentId),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );
    }
}
