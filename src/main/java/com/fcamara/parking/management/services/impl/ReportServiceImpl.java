package com.fcamara.parking.management.services.impl;

import com.fcamara.parking.management.dtos.HourlyParkingSummaryDTO;
import com.fcamara.parking.management.dtos.VehicleMovementReportDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.repositorys.ParkingTransactionRepository;
import com.fcamara.parking.management.services.EstablishmentService;
import com.fcamara.parking.management.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ParkingTransactionRepository parkingTransactionRepository;

    private final EstablishmentService establishmentService;

    @Override
    public ParkingSummaryResponseDTO getSummary(String establishmentId) {
        establishmentService.getById(establishmentId);

        var totalEntries = parkingTransactionRepository.countByEntryDateIsNotNullAndEstablishmentId(establishmentId);

        var totalExits = parkingTransactionRepository.countByExitDateIsNotNullAndEstablishmentId(establishmentId);

        return ParkingSummaryResponseDTO.toDTO(totalEntries, totalExits);
    }

    @Override
    public List<HourlyParkingSummaryDTO> getHourlySummary(String establishmentId) {
        establishmentService.getById(establishmentId);

        return IntStream.range(0, 24)
                .mapToObj(hour -> createHourlySummary(establishmentId, hour))
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleMovementReportDTO> getVehicleMovementReport(String establishmentId, String startDate, String endDate) {
        establishmentService.getById(establishmentId);

        var startOfDay = LocalDate.parse(startDate).atStartOfDay();

        var endOfDay = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        var parkingTransactions = parkingTransactionRepository
                .findAllByEstablishmentIdAndEntryDateBetween(establishmentId, startOfDay, endOfDay);

        return parkingTransactions.stream()
                .map(VehicleMovementReportDTO::toDTO)
                .collect(Collectors.toList());
    }

    private HourlyParkingSummaryDTO createHourlySummary(String establishmentId, int hour) {
        var currentHour = LocalDate.now().atTime(hour, 0);
        var nextHour = currentHour.plusHours(1);

        long entries = parkingTransactionRepository.countByEntryDateBetweenAndEstablishmentId(currentHour, nextHour, establishmentId);
        long exits = parkingTransactionRepository.countByExitDateBetweenAndEstablishmentId(currentHour, nextHour, establishmentId);

        return HourlyParkingSummaryDTO.toDTO(
                currentHour.format(DateTimeFormatter.ofPattern("HH:mm")),
                (int) entries,
                (int) exits
        );
    }
}
