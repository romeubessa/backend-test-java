package com.fcamara.parking.management.services;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.models.ParkingTransaction;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.repositorys.ParkingTransactionRepository;
import com.fcamara.parking.management.services.EstablishmentService;
import com.fcamara.parking.management.services.VehicleService;
import com.fcamara.parking.management.services.impl.ParkingTransactionTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.fcamara.parking.management.enums.MessagesEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParkingTransactionServiceTest {

    @InjectMocks
    private ParkingTransactionTransactionServiceImpl parkingTransactionService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private EstablishmentService establishmentService;

    @Mock
    private ParkingTransactionRepository parkingTransactionRepository;

    @Test
    void registerEntry_Success() {
        Establishment establishment = new Establishment();
        establishment.setCarSpots(1);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle1");
        vehicle.setType(VehicleTypeEnum.CAR);

        when(establishmentService.getById(anyString())).thenReturn(establishment);
        when(vehicleService.getByPlate(anyString())).thenReturn(vehicle);
        when(parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(anyString())).thenReturn(Optional.empty());
        when(parkingTransactionRepository.save(any(ParkingTransaction.class))).thenReturn(new ParkingTransaction());

        ParkingTransaction transaction = parkingTransactionService.registerEntry("establishment1", "ABC1234");

        assertNotNull(transaction);

        verify(parkingTransactionRepository, times(1)).save(any(ParkingTransaction.class));
    }

    @Test
    void registerEntry_VehicleAlreadyParked() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle1");

        when(establishmentService.getById(anyString())).thenReturn(new Establishment());
        when(vehicleService.getByPlate(anyString())).thenReturn(vehicle);
        when(parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(anyString())).thenReturn(Optional.of(new ParkingTransaction()));

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            parkingTransactionService.registerEntry("establishment1", "ABC1234");
        });

        assertEquals(PHISICAL_PARKING_01.getCode(), exception.getCode());

        verify(parkingTransactionRepository, never()).save(any(ParkingTransaction.class));
    }

    @Test
    void registerExit_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle1");

        ParkingTransaction parkingTransaction = new ParkingTransaction();
        parkingTransaction.setId("transaction1");

        when(vehicleService.getByPlate(anyString())).thenReturn(vehicle);
        when(establishmentService.getById(anyString())).thenReturn(new Establishment());
        when(parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(anyString())).thenReturn(Optional.of(parkingTransaction));
        when(parkingTransactionRepository.save(any(ParkingTransaction.class))).thenReturn(parkingTransaction);

        ParkingTransaction transaction = parkingTransactionService.registerExit("establishment1", "ABC1234");

        assertNotNull(transaction);

        assertNotNull(transaction.getExitDate());

        verify(parkingTransactionRepository, times(1)).save(parkingTransaction);
    }

    @Test
    void registerExit_VehicleNotParked() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle1");

        when(vehicleService.getByPlate(anyString())).thenReturn(vehicle);
        when(establishmentService.getById(anyString())).thenReturn(new Establishment());
        when(parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(anyString())).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            parkingTransactionService.registerExit("establishment1", "ABC1234");
        });

        assertEquals(PHISICAL_PARKING_02.getCode(), exception.getCode());

        verify(parkingTransactionRepository, never()).save(any(ParkingTransaction.class));
    }

    @Test
    void registerEntry_ExceedsMaxSpots() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle1");
        vehicle.setType(VehicleTypeEnum.CAR);

        Establishment establishment = new Establishment();
        establishment.setCarSpots(10);

        when(establishmentService.getById(anyString())).thenReturn(establishment);
        when(vehicleService.getByPlate(anyString())).thenReturn(vehicle);
        when(parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(anyString())).thenReturn(Optional.empty());
        when(parkingTransactionRepository.countByEstablishmentAndVehicle_TypeAndExitDateIsNull(any(Establishment.class), any(VehicleTypeEnum.class)))
                .thenReturn(10L);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            parkingTransactionService.registerEntry("establishment1", "ABC1234");
        });

        assertEquals(PHISICAL_PARKING_03.getCode(), exception.getCode());

        verify(parkingTransactionRepository, never()).save(any(ParkingTransaction.class));
    }
}
