package com.fcamara.parking.management.repositorys;

import com.fcamara.parking.management.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findByPlateAndIdNot(String plate, String id);

    Optional<Vehicle> findByPlate(String plate);
}
