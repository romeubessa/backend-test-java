package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.HourlyParkingSummaryDTO;
import com.fcamara.parking.management.dtos.responses.HourlyParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingSummaryResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingTransactionResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleMovementReportResponseDTO;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.ParkingTransaction;
import com.fcamara.parking.management.services.ParkingTransactionService;
import com.fcamara.parking.management.services.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Objects;

import static com.fcamara.parking.management.enums.MessagesEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSummary_Success() {
        ParkingSummaryResponseDTO summaryResponse = new ParkingSummaryResponseDTO(10, 5);

        when(reportService.getSummary(anyString())).thenReturn(summaryResponse);

        ResponseEntity<ParkingSummaryResponseDTO> response = reportController.getSummary("1");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(10, Objects.requireNonNull(response.getBody()).getTotalEntries());

        assertEquals(5, Objects.requireNonNull(response.getBody()).getTotalExits());
    }

    @Test
    void getSummary_NotFound() throws Exception {
        doThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode())).when(reportService).getSummary(anyString());

        mockMvc.perform(get("/reports/summary/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }

    @Test
    void getHourlySummary_Success() {
        var hourlySummary = Collections.singletonList(new HourlyParkingSummaryDTO());

        when(reportService.getHourlySummary(anyString())).thenReturn(hourlySummary);

        ResponseEntity<HourlyParkingSummaryResponseDTO> response = reportController.getHourlySummary("1");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, Objects.requireNonNull(response.getBody()).getSummaries().size());
    }

    @Test
    void getHourlySummary_NotFound() throws Exception {
        doThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode())).when(reportService).getHourlySummary("1");

        mockMvc.perform(get("/reports/hourly-summary/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }

    @Test
    void getVehicleMovementReport_Success() {
        when(reportService.getVehicleMovementReport(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<VehicleMovementReportResponseDTO> response = reportController.getVehicleMovementReport("1", "2024-01-01", "2024-01-31");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(0, response.getBody().getMovements().size());
    }

    @Test
    void getVehicleMovementReport_NotFound() throws Exception {
        String establishmentId = "12345";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";

        doThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode())).when(reportService).getVehicleMovementReport(establishmentId, startDate, endDate);

        mockMvc.perform(get("/reports/vehicle-movements/" + establishmentId)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }
}
