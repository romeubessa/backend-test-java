package com.fcamara.parking.management.services.impl;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.repositorys.EstablishmentRepository;
import com.fcamara.parking.management.services.EstablishmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_01;
import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_02;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstablishmentServiceImpl implements EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    @Override
    public Establishment save(Establishment establishment) {
        establishmentRepository.findByCnpjAndIdNot(establishment.getCnpj(), establishment.getId()).ifPresent(v -> {
            throw new BusinessRuleException(PHISICAL_ESTABLISHMENT_01.getCode());
        });

        return establishmentRepository.save(establishment);
    }

    @Override
    public Establishment update(String id, Establishment establishment) {
        getById(id);

        establishment.setId(id);

        return save(establishment);
    }

    @Override
    public List<Establishment> getAll() {
        return establishmentRepository.findAll();
    }

    @Override
    public Establishment getById(String id) {
        return establishmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PHISICAL_ESTABLISHMENT_02.getCode()));
    }

    @Override
    public void deleteById(String id) {
        getById(id);

        establishmentRepository.deleteById(id);
    }
}
