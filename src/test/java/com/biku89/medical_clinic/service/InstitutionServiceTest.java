package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class InstitutionServiceTest {
    InstitutionService institutionService;
    InstitutionMapper institutionMapper;
    InstitutionRepository institutionRepository;

    @BeforeEach
    void setUp() {
        this.institutionMapper = Mappers.getMapper(InstitutionMapper.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionService = new InstitutionService(this.institutionRepository, this.institutionMapper);
    }

    @Test
    void getAllInstitutions_TestPageable_returnedInstitutions() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Institution> institutions = new ArrayList<>();
        Institution institution = new Institution(1L, "szpital", "Łódź", "0", "ulica", "1", new ArrayList<>());
        Institution institution1 = new Institution(2L, "Zakład", "Kraków", "0", "ulica", "1", new ArrayList<>());
        institutions.add(institution);
        institutions.add(institution1);
        Page<Institution> page = new PageImpl<>(institutions);
        when(institutionRepository.findAll(pageable)).thenReturn(page);

        PageDTO<InstitutionDTO> result = institutionService.getAllInstitutions(pageable);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.getContent().size()),
                () -> Assertions.assertEquals("szpital", result.getContent().getFirst().name()),
                () -> Assertions.assertEquals("Zakład", result.getContent().get(1).name())
        );
    }

    @Test
    void getInstitutionsById_InstitutionExists_ReturnedInstitution() {
        Long id = 1L;
        Institution institution = new Institution(1L, "szpital", "Łódź", "0", "ulica", "1", new ArrayList<>());
        when(institutionRepository.findById(id)).thenReturn(Optional.of(institution));

        InstitutionDTO result = institutionService.getInstitutionsById(id);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("szpital", result.name()),
                () -> Assertions.assertEquals("Łódź", result.city())
        );
    }

    @Test
    void getInstitutionsById_InstitutionsNotFound_ReturnedException() {
        Long id = 1L;
        when(institutionRepository.findById(id)).thenReturn(Optional.empty());

        InstitutionNotFoundException exception = Assertions.assertThrows(InstitutionNotFoundException.class, () -> institutionService.getInstitutionsById(id));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Institution not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void addInstitution_InstitutionNotExists_returnAddInstitution() {
        String name = "szpital";
        InstitutionDTO institution = new InstitutionDTO(1L, name, "łódź", "0", "tak", "1", new ArrayList<>());
        when(institutionRepository.findByName(name)).thenReturn(Optional.empty());

        InstitutionDTO result = institutionService.addInstitution(institution);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("szpital", result.name()),
                () -> Assertions.assertEquals("0", result.postalCode())
        );
    }

    @Test
    void addInstitution_InstitutionExists_returnedException() {
        String name = "szpital";
        Institution institution = new Institution(1L, name, "Łódź", "0", "ulica", "1", new ArrayList<>());
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, name, "łódź", "0", "tak", "1", new ArrayList<>());
        when(institutionRepository.findByName(name)).thenReturn(Optional.of(institution));

        InstitutionExistingException exception = Assertions.assertThrows(InstitutionExistingException.class, () -> institutionService.addInstitution(institutionDTO));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Institution with this name already exists", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus())
        );
    }
    @Test
    void deleteInstitution_InstitutionExists_returnedDeleteInstitution(){
        String name = "szpital";
        Institution institution = new Institution(1L, name, "Łódź", "0", "ulica", "1", new ArrayList<>());
        when(institutionRepository.findByName(name)).thenReturn(Optional.of(institution));

        institutionService.deleteInstitution(name);

        verify(institutionRepository, times(1)).delete(institution);
    }
}
