package com.fcamara.parking.management.service;

import com.fcamara.parking.management.dtos.responses.HourlyParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleMovementReportResponseDTO;

import java.util.List;

public interface ReportService {

    ParkingSummaryResponseDTO getSummary(String establishmentId);

    List<HourlyParkingSummaryResponseDTO> getHourlySummary(String establishmentId);

    List<VehicleMovementReportResponseDTO> getVehicleMovementReport(String establishmentId, String startDate, String endDate);
}
