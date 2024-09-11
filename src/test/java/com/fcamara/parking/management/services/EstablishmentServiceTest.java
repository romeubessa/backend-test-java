package com.fcamara.parking.management.services;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import com.fcamara.parking.management.exceptions.NotFoundException;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.repositorys.EstablishmentRepository;
import com.fcamara.parking.management.services.impl.EstablishmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_01;
import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_ESTABLISHMENT_02;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class EstablishmentServiceTest {

    @InjectMocks
    private EstablishmentServiceImpl establishmentService;

    @Mock
    private EstablishmentRepository establishmentRepository;

    @Test
    void save_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setCnpj("12345678000199");

        when(establishmentRepository.save(any(Establishment.class))).thenReturn(establishment);

        Establishment result = establishmentService.save(establishment);

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(establishmentRepository, times(1)).save(any(Establishment.class));
    }

    @Test
    void save_Error_CnpjAlreadyExists() {
        Establishment establishment = new Establishment();
        establishment.setId("1");
        establishment.setCnpj("12345678000199");

        when(establishmentRepository.findByCnpjAndIdNot(anyString(), anyString()))
                .thenReturn(Optional.of(new Establishment()));

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> establishmentService.save(establishment));

        assertEquals(PHISICAL_ESTABLISHMENT_01.getCode(), exception.getCode());

        verify(establishmentRepository, never()).save(any(Establishment.class));
    }

    @Test
    void update_Success() {
        Establishment existingEstablishment = new Establishment();
        existingEstablishment.setId("1");

        when(establishmentRepository.findById(anyString())).thenReturn(Optional.of(existingEstablishment));

        when(establishmentRepository.save(any(Establishment.class))).thenReturn(existingEstablishment);

        Establishment updatedEstablishment = new Establishment();
        updatedEstablishment.setCnpj("12345678000199");

        Establishment result = establishmentService.update("1", updatedEstablishment);

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(establishmentRepository, times(1)).findById(anyString());

        verify(establishmentRepository, times(1)).save(any(Establishment.class));
    }

    @Test
    void update_Error_EstablishmentNotFound() {
        when(establishmentRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> establishmentService.update("1", new Establishment()));

        assertEquals(PHISICAL_ESTABLISHMENT_02.getCode(), exception.getCode());

        verify(establishmentRepository, never()).save(any(Establishment.class));
    }

    @Test
    void getAll_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");

        when(establishmentRepository.findAll()).thenReturn(Collections.singletonList(establishment));

        List<Establishment> result = establishmentService.getAll();

        assertNotNull(result);

        assertEquals(1, result.size());

        assertEquals("1", result.get(0).getId());

        verify(establishmentRepository, times(1)).findAll();
    }

    @Test
    void getById_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");

        when(establishmentRepository.findById(anyString())).thenReturn(Optional.of(establishment));

        Establishment result = establishmentService.getById("1");

        assertNotNull(result);

        assertEquals("1", result.getId());

        verify(establishmentRepository, times(1)).findById(anyString());
    }

    @Test
    void getById_Error_EstablishmentNotFound() {
        when(establishmentRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> establishmentService.getById("1"));

        assertEquals(PHISICAL_ESTABLISHMENT_02.getCode(), exception.getCode());

        verify(establishmentRepository, times(1)).findById(anyString());
    }

    @Test
    void deleteById_Success() {
        Establishment establishment = new Establishment();
        establishment.setId("1");

        when(establishmentRepository.findById(anyString())).thenReturn(Optional.of(establishment));

        establishmentService.deleteById("1");

        verify(establishmentRepository, times(1)).deleteById(anyString());

        verify(establishmentRepository, times(1)).findById(anyString());
    }

    @Test
    void deleteById_Error_EstablishmentNotFound() {
        when(establishmentRepository.findById(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> establishmentService.deleteById("1"));

        assertEquals(PHISICAL_ESTABLISHMENT_02.getCode(), exception.getCode());
        verify(establishmentRepository, never()).deleteById(anyString());
    }
}
