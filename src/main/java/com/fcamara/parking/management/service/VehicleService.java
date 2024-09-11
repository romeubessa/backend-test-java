package com.fcamara.parking.management.service;

import com.fcamara.parking.management.models.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle save(Vehicle vehicle);

    Vehicle update(String id, Vehicle vehicle);

    List<Vehicle> getAll();

    Vehicle getById(String id);

    Vehicle getByPlate(String plate);

    void deleteById(String id);
}
