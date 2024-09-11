package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.responses.ParkingTransactionResponseDTO;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.ParkingTransaction;
import com.fcamara.parking.management.services.ParkingTransactionService;
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

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_02;
import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_VEHICLE_04;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingControllerTest {

    @Mock
    private ParkingTransactionService parkingTransactionService;

    @InjectMocks
    private ParkingController parkingController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerEntry_Success() {
        ParkingTransaction parkingTransaction = new ParkingTransaction();
        parkingTransaction.setId("1");

        when(parkingTransactionService.registerEntry(anyString(), anyString())).thenReturn(parkingTransaction);

        ResponseEntity<ParkingTransactionResponseDTO> response = parkingController.registerEntry("1", "ABC");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void registerEntry_Failure_NotFound() throws Exception {
        doThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode())).when(parkingTransactionService)
                .registerEntry(anyString(), anyString());

        mockMvc.perform(post("/parking/entry")
                        .param("establishmentId", "1")
                        .param("plate", "ABC1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }

    @Test
    void registerExit_Success() {
        ParkingTransaction parkingTransaction = new ParkingTransaction();
        parkingTransaction.setId("1");

        when(parkingTransactionService.registerExit(anyString(), anyString())).thenReturn(parkingTransaction);

        ResponseEntity<ParkingTransactionResponseDTO> response = parkingController.registerExit("1", "ABC");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void registerExit_Failure_NotFound() throws Exception {
        doThrow(new NotFoundException(PHISICAL_VEHICLE_04.getCode())).when(parkingTransactionService)
                .registerExit(anyString(), anyString());

        mockMvc.perform(post("/parking/exit")
                        .param("establishmentId", "1")
                        .param("plate", "ABC1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle not found with plate ABC1234."));
    }
}
