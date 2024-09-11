package com.fcamara.parking.management.services.impl;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.repositorys.VehicleRepository;
import com.fcamara.parking.management.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fcamara.parking.management.enums.MessagesEnum.*;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicleRepository.findByPlateAndIdNot(vehicle.getPlate(), vehicle.getId()).ifPresent(v -> {
            throw new BusinessRuleException(PHISICAL_VEHICLE_01.getCode());
        });

        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(String id, Vehicle vehicle) {
        getById(id);

        vehicle.setId(id);

        return save(vehicle);
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getById(String id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PHISICAL_VEHICLE_03.getCode()));
    }

    @Override
    public Vehicle getByPlate(String plate) {
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new NotFoundException(PHISICAL_VEHICLE_04.getCode(), plate));
    }

    @Override
    public void deleteById(String id) {
        getById(id);

        vehicleRepository.deleteById(id);
    }
}
