package com.fcamara.parking.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcamara.parking.management.dtos.requests.VehicleRequestDTO;
import com.fcamara.parking.management.dtos.responses.VehicleResponseDTO;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.services.VehicleService;
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

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_VEHICLE_03;
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
public class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createVehicle_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setBrand("Toyota");

        VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO();
        vehicleRequestDTO.setBrand("Toyota");
        vehicleRequestDTO.setType("CAR");

        when(vehicleService.save(any(Vehicle.class))).thenReturn(vehicle);

        ResponseEntity<VehicleResponseDTO> response = vehicleController.createVehicle(vehicleRequestDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Toyota", Objects.requireNonNull(response.getBody()).getVehicles().get(0).getBrand());
    }

    @Test
    void createVehicle_Failure_ValidationError() throws Exception {
        String invalidRequest = "{}";

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getAllVehicles_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setBrand("Toyota");

        when(vehicleService.getAll()).thenReturn(Collections.singletonList(vehicle));

        ResponseEntity<VehicleResponseDTO> response = vehicleController.getAllVehicles();

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, Objects.requireNonNull(response.getBody()).getVehicles().size());
    }

    @Test
    void getVehicleById_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setBrand("Toyota");

        when(vehicleService.getById(anyString())).thenReturn(vehicle);

        ResponseEntity<VehicleResponseDTO> response = vehicleController.getVehicleById("1");

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Toyota", Objects.requireNonNull(response.getBody()).getVehicles().get(0).getBrand());
    }

    @Test
    void getVehicleById_NotFound() throws Exception {
        when(vehicleService.getById("1")).thenThrow(new NotFoundException(PHISICAL_VEHICLE_03.getCode()));

        mockMvc.perform(get("/vehicles/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle not found with the provided ID."));
    }

    @Test
    void deleteVehicleById_Success() {
        ResponseEntity<Void> response = vehicleController.deleteVehicleById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteVehicleById_NotFound() throws Exception {
        doThrow(new NotFoundException("Vehicle not found")).when(vehicleService).deleteById("1");

        mockMvc.perform(delete("/vehicles/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle not found with the provided ID."));
    }

    @Test
    void updateVehicleById_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setBrand("Toyota Updated");

        VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO();
        vehicleRequestDTO.setBrand("Toyota Updated");
        vehicleRequestDTO.setType("CAR");

        when(vehicleService.update(anyString(), any(Vehicle.class))).thenReturn(vehicle);

        ResponseEntity<VehicleResponseDTO> response = vehicleController.updateVehicleById("1", vehicleRequestDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("Toyota Updated", Objects.requireNonNull(response.getBody()).getVehicles().get(0).getBrand());
    }

    @Test
    void updateVehicle_Failure_ValidationError() throws Exception {
        String invalidRequest = "{}";

        mockMvc.perform(put("/vehicles/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateVehicleById_Failure_NotFound() throws Exception {
        String nonExistentId = "12345";
        VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO();
        vehicleRequestDTO.setBrand("Toyota Updated");
        vehicleRequestDTO.setModel("Model");
        vehicleRequestDTO.setColor("Color");
        vehicleRequestDTO.setPlate("Plate");
        vehicleRequestDTO.setType("CAR");

        doThrow(new NotFoundException("Vehicle not found")).when(vehicleService).update(eq(nonExistentId), any(Vehicle.class));

        mockMvc.perform(put("/vehicles/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString((vehicleRequestDTO))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehicle not found with the provided ID."));
    }
}
