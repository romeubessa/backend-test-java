package com.fcamara.parking.management.repositorys;

import com.fcamara.parking.management.enums.VehicleTypeEnum;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.models.ParkingTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTransactionRepository extends MongoRepository<ParkingTransaction, String> {

    Optional<ParkingTransaction> findByVehicleIdAndExitDateIsNull(String vehicleId);

    long countByEstablishmentAndVehicle_TypeAndExitDateIsNull(Establishment establishment, VehicleTypeEnum vehicleType);

    long countByEntryDateIsNotNullAndEstablishmentId(String establishmentId);

    long countByExitDateIsNotNullAndEstablishmentId(String establishmentId);

    long countByEntryDateBetweenAndEstablishmentId(LocalDateTime current, LocalDateTime next, String establishmentId);

    long countByExitDateBetweenAndEstablishmentId(LocalDateTime current, LocalDateTime next, String establishmentId);

    List<ParkingTransaction> findAllByEstablishmentIdAndEntryDateBetween(String establishmentId, LocalDateTime start, LocalDateTime end);
}
