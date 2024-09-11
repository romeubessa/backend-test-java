package com.fcamara.parking.management.service.impl;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.models.ParkingTransaction;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.repositorys.ParkingTransactionRepository;
import com.fcamara.parking.management.service.EstablishmentService;
import com.fcamara.parking.management.service.ParkingTransactionService;
import com.fcamara.parking.management.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.fcamara.parking.management.enums.MessagesEnum.*;

@RequiredArgsConstructor
@Service
public class ParkingTransactionTransactionServiceImpl implements ParkingTransactionService {

    private final VehicleService vehicleService;

    private final EstablishmentService establishmentService;

    private final ParkingTransactionRepository parkingTransactionRepository;

    @Override
    public ParkingTransaction registerEntry(String establishmentId, String plate) {
        var establishment = establishmentService.getById(establishmentId);

        var vehicle = vehicleService.getByPlate(plate);

        parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(vehicle.getId())
                .ifPresent(transaction -> {
                    throw new BusinessRuleException(PHISICAL_PARKING_01.getCode());
                });

        validateParkingAvailability(establishment, vehicle);

        return parkingTransactionRepository.save(ParkingTransaction.toModel(establishment, vehicle));
    }

    @Override
    public ParkingTransaction registerExit(String establishmentId, String plate) {
        var vehicle = vehicleService.getByPlate(plate);

        establishmentService.getById(establishmentId);

        var parkingTransaction = parkingTransactionRepository.findByVehicleIdAndExitDateIsNull(vehicle.getId())
                .orElseThrow(() -> new BusinessRuleException(PHISICAL_PARKING_02.getCode()));

        parkingTransaction.setExitDate(LocalDateTime.now());

        return parkingTransactionRepository.save(parkingTransaction);
    }

    private void validateParkingAvailability(Establishment establishment, Vehicle vehicle) {
        var vehicleType = vehicle.getType();

        var parkedVehicles = parkingTransactionRepository.
                countByEstablishmentAndVehicle_TypeAndExitDateIsNull(establishment, vehicleType);

        var maxSpots = establishment.getMaxSpotsByVehicleType(vehicleType);

        if (parkedVehicles >= maxSpots) {
            throw new BusinessRuleException(PHISICAL_PARKING_03.getCode(), maxSpots);
        }
    }
}
