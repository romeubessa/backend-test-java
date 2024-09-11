package com.fcamara.parking.management.services;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.repositorys.VehicleRepository;
import com.fcamara.parking.management.services.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_VEHICLE_01;
import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_VEHICLE_03;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VehicleServiceTest {

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Test
    void save_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setPlate("ABC1234");

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.save(vehicle);

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void save_Error_PlateAlreadyExists() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");
        vehicle.setPlate("ABC1234");

        when(vehicleRepository.findByPlateAndIdNot(anyString(), anyString()))
                .thenReturn(Optional.of(new Vehicle()));

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> vehicleService.save(vehicle));

        assertEquals(PHISICAL_VEHICLE_01.getCode(), exception.getCode());

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void update_Success() {
        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setId("1");

        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(existingVehicle);

        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setPlate("ABC1234");

        Vehicle result = vehicleService.update("1", updatedVehicle);

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(vehicleRepository, times(1)).findById(anyString());

        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void update_Error_VehicleNotFound() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> vehicleService.update("1", new Vehicle()));

        assertEquals(PHISICAL_VEHICLE_03.getCode(), exception.getCode());

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void getAll_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");

        when(vehicleRepository.findAll()).thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> result = vehicleService.getAll();

        assertNotNull(result);

        assertEquals(1, result.size());

        assertEquals("1", result.get(0).getId());

        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void getById_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");

        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getById("1");

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(vehicleRepository, times(1)).findById(anyString());
    }

    @Test
    void getById_Error_VehicleNotFound() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> vehicleService.getById("1"));

        assertEquals(PHISICAL_VEHICLE_03.getCode(), exception.getCode());

        verify(vehicleRepository, times(1)).findById(anyString());
    }

    @Test
    void deleteById_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("1");

        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicle));

        vehicleService.deleteById("1");

        verify(vehicleRepository, times(1)).deleteById(anyString());

        verify(vehicleRepository, times(1)).findById(anyString());
    }

    @Test
    void deleteById_Error_VehicleNotFound() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> vehicleService.deleteById("1"));

        assertEquals(PHISICAL_VEHICLE_03.getCode(), exception.getCode());

        verify(vehicleRepository, never()).deleteById(anyString());
    }
}
