package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.responses.ErrorResponseDTO;
import com.fcamara.parking.management.dtos.responses.HourlyParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleMovementReportResponseDTO;
import com.fcamara.parking.management.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Parking Reports", description = "API for generating reports on vehicle entries, exits, and hourly summaries within the parking system.")
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/summary/{establishmentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get parking summary", description = "Generates a summary of vehicle entries and exits for the specified establishment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved summary",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingSummaryResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ParkingSummaryResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<ParkingSummaryResponseDTO> getSummary(@PathVariable String establishmentId) {
        var summary = reportService.getSummary(establishmentId);

        return ResponseEntity.ok(summary);
    }

    @GetMapping(value = "/hourly-summary/{establishmentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get hourly parking summary", description = "Returns the total entries and exits of vehicles by hour for a specified establishment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved hourly summary",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HourlyParkingSummaryResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = HourlyParkingSummaryResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<HourlyParkingSummaryResponseDTO> getHourlySummary(@PathVariable String establishmentId) {
        var hourlySummary = reportService.getHourlySummary(establishmentId);

        return ResponseEntity.ok(HourlyParkingSummaryResponseDTO.toDTO(hourlySummary));
    }

    @GetMapping(value = "/vehicle-movements/{establishmentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get vehicle movement report for an establishment", description = "Fetches the total vehicle entries and exits for the specified establishment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched vehicle movement report",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VehicleMovementReportResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleMovementReportResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<VehicleMovementReportResponseDTO> getVehicleMovementReport(
            @PathVariable String establishmentId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        var vehicleMovements = reportService.getVehicleMovementReport(establishmentId, startDate, endDate);

        return ResponseEntity.ok(VehicleMovementReportResponseDTO.toDTO(vehicleMovements));
    }
}