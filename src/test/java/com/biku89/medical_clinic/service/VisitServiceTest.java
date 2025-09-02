package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

public class VisitServiceTest {
    VisitService visitService;
    VisitRepository visitRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitMapper visitMapper;

    @BeforeEach
    void setUp(){
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.visitMapper = Mappers.getMapper(VisitMapper.class);
        this.visitService = new VisitService(this.visitRepository, this.doctorRepository, this.patientRepository, this.visitMapper);
    }

    @Test
    void createVisit_doctorExistsAndNoConflicts_returnedCreateVisit(){
        Long id = 1L;
        Doctor doctor = new Doctor(id,"jan","kowalski", "jankowalski@gmail.com","hasło",new ArrayList<>());
        LocalDateTime startVisit = LocalDateTime.of(2026,1,1,10, 0);
        LocalDateTime endVisit = LocalDateTime.of(2026,1,1,10, 15);
        CreateVisitCommand createVisitCommand = new CreateVisitCommand(startVisit,endVisit);

        Visit savedVisit = new Visit();
        savedVisit.setId(10L);
        savedVisit.setDoctor(doctor);
        savedVisit.setStartDateTime(startVisit);
        savedVisit.setEndDateTime(endVisit);

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(visitRepository.save(Mockito.any(Visit.class))).thenReturn(savedVisit);

        VisitDTO result = visitService.createVisit(id, createVisitCommand);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.doctor().id())
        );
    }
}
