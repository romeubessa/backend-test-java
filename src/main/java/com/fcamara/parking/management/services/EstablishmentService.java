package com.fcamara.parking.management.services;

import com.fcamara.parking.management.models.Establishment;

import java.util.List;

public interface EstablishmentService {

    Establishment save(Establishment establishment);

    Establishment update(String id, Establishment establishment);

    List<Establishment> getAll();

    Establishment getById(String id);

    void deleteById(String id);
}
