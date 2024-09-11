package com.fcamara.parking.management.services;

import com.fcamara.parking.management.dtos.HourlyParkingSummaryDTO;
import com.fcamara.parking.management.dtos.VehicleMovementReportDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;

import java.util.List;

public interface ReportService {

    ParkingSummaryResponseDTO getSummary(String establishmentId);

    List<HourlyParkingSummaryDTO> getHourlySummary(String establishmentId);

    List<VehicleMovementReportDTO> getVehicleMovementReport(String establishmentId, String startDate, String endDate);
}
