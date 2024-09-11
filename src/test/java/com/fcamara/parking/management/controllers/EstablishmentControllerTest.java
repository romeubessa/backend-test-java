package com.fcamara.parking.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcamara.parking.management.dtos.requests.EstablishmentRequestDTO;
import com.fcamara.parking.management.dtos.responses.EstablishmentResponseDTO;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.services.EstablishmentService;
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

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_02;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EstablishmentControllerTest {

    @Mock
    private EstablishmentService establishmentService;

    @InjectMocks
    private EstablishmentController establishmentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEstablishment_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setName("Parking Lot");

        EstablishmentRequestDTO establishmentRequestDTO = new EstablishmentRequestDTO();
        establishmentRequestDTO.setName("Parking Lot");

        when(establishmentService.save(any(Establishment.class))).thenReturn(establishment);

        ResponseEntity<EstablishmentResponseDTO> response = establishmentController.createEstablishment(establishmentRequestDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Parking Lot", Objects.requireNonNull(response.getBody()).getEstablishments().get(0).getName());
    }

    @Test
    void createEstablishment_Failure_ValidationError() throws Exception {
        String invalidRequest = "{}";

        mockMvc.perform(post("/establishments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getAllEstablishments_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setName("Parking Lot");

        when(establishmentService.getAll()).thenReturn(Collections.singletonList(establishment));

        ResponseEntity<EstablishmentResponseDTO> response = establishmentController.getAllEstablishments();

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, Objects.requireNonNull(response.getBody()).getEstablishments().size());
    }

    @Test
    void getEstablishmentById_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setName("Parking Lot");

        when(establishmentService.getById(anyString())).thenReturn(establishment);

        ResponseEntity<EstablishmentResponseDTO> response = establishmentController.getEstablishmentById("1");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Parking Lot", Objects.requireNonNull(response.getBody()).getEstablishments().get(0).getName());
    }

    @Test
    void getEstablishmentById_NotFound() throws Exception {
        when(establishmentService.getById("1")).thenThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode()));

        mockMvc.perform(get("/establishments/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }

    @Test
    void deleteEstablishmentById_Success() {
        ResponseEntity<Void> response = establishmentController.deleteEstablishmentById("1");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteEstablishmentById_NotFound() throws Exception {
        doThrow(new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode())).when(establishmentService).deleteById("1");

        mockMvc.perform(delete("/establishments/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }

    @Test
    void updateEstablishmentById_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setName("Updated Name");

        EstablishmentRequestDTO establishmentRequestDTO = new EstablishmentRequestDTO();
        establishmentRequestDTO.setName("Updated Name");

        when(establishmentService.update(anyString(), any(Establishment.class))).thenReturn(establishment);

        ResponseEntity<EstablishmentResponseDTO> response = establishmentController.updateEstablishmentById("1", establishmentRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Name", Objects.requireNonNull(response.getBody()).getEstablishments().get(0).getName());
    }

    @Test
    void updateEstablishment_Failure_ValidationError() throws Exception {
        String invalidRequest = "{}";

        mockMvc.perform(put("/establishments/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateEstablishmentById_Failure_NotFound() throws Exception {
        String nonExistentId = "12345";
        EstablishmentRequestDTO establishmentRequestDTO = new EstablishmentRequestDTO();
        establishmentRequestDTO.setName("Updated Parking Lot");
        establishmentRequestDTO.setCnpj("40145073000196");
        establishmentRequestDTO.setAddress("Address");
        establishmentRequestDTO.setPhone("(67) 98164-2846");
        establishmentRequestDTO.setMotorcycleSpots(0);
        establishmentRequestDTO.setCarSpots(0);

        doThrow(new NotFoundException("Establishment not found")).when(establishmentService).update(eq(nonExistentId), any(Establishment.class));

        mockMvc.perform(put("/establishments/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString((establishmentRequestDTO))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Establishment not found with the provided ID."));
    }
}
