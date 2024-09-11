package com.fcamara.parking.management.repositorys;

import com.fcamara.parking.management.models.Establishment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstablishmentRepository extends MongoRepository<Establishment, String> {

    Optional<Establishment> findByCnpjAndIdNot(String cnpj, String id);
}
