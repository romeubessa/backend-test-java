package com.fcamara.parking.management.service.impl;

import com.fcamara.parking.management.dtos.responses.HourlyParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleMovementReportResponseDTO;
import com.fcamara.parking.management.repositorys.ParkingTransactionRepository;
import com.fcamara.parking.management.service.EstablishmentService;
import com.fcamara.parking.management.service.ReportService;
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
    public List<HourlyParkingSummaryResponseDTO> getHourlySummary(String establishmentId) {
        establishmentService.getById(establishmentId);

        return IntStream.range(0, 24)
                .mapToObj(hour -> createHourlySummary(establishmentId, hour))
                .toList();
    }

    @Override
    public List<VehicleMovementReportResponseDTO> getVehicleMovementReport(String establishmentId, String startDate, String endDate) {
        establishmentService.getById(establishmentId);

        var startOfDay = LocalDate.parse(startDate).atStartOfDay();

        var endOfDay = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        var parkingTransactions = parkingTransactionRepository
                .findAllByEstablishmentIdAndEntryDateBetween(establishmentId, startOfDay, endOfDay);

        return parkingTransactions.stream()
                .map(VehicleMovementReportResponseDTO::toDTO)
                .collect(Collectors.toList());

    }

    private HourlyParkingSummaryResponseDTO createHourlySummary(String establishmentId, int hour) {
        var currentHour = LocalDate.now().atTime(hour, 0);
        var nextHour = currentHour.plusHours(1);

        long entries = parkingTransactionRepository.countByEntryDateBetweenAndEstablishmentId(currentHour, nextHour, establishmentId);
        long exits = parkingTransactionRepository.countByExitDateBetweenAndEstablishmentId(currentHour, nextHour, establishmentId);

        return HourlyParkingSummaryResponseDTO.toDTO(
                currentHour.format(DateTimeFormatter.ofPattern("HH:mm")),
                (int) entries,
                (int) exits
        );
    }
}
