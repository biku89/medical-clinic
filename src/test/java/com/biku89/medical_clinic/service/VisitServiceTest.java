/*
package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VisitServiceTest {
    VisitService visitService;
    VisitRepository visitRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitMapper visitMapper;

    @BeforeEach
    void setUp() {
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.visitMapper = Mappers.getMapper(VisitMapper.class);
        this.visitService = new VisitService(this.visitRepository, this.doctorRepository, this.patientRepository, this.visitMapper);
    }

    @Test
    void createVisit_doctorExistsAndNoConflicts_returnedCreateVisit() {
        Long id = 1L;
        Doctor doctor = new Doctor(id, "jan", "kowalski", "jankowalski@gmail.com", "hasło", new ArrayList<>());
        LocalDateTime startVisit = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime endVisit = LocalDateTime.of(2026, 1, 1, 10, 15);
        CreateVisitCommand createVisitCommand = new CreateVisitCommand(startVisit, endVisit);

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

    @Test
    void createVisit_doctorNotExists_returnedException() {
        Long id = 1L;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());
        LocalDateTime startVisit = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime endVisit = LocalDateTime.of(2026, 1, 1, 10, 15);
        CreateVisitCommand createVisitCommand = new CreateVisitCommand(startVisit, endVisit);

        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class, () -> visitService.createVisit(id, createVisitCommand));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void bookVisit_VisitAndPatientExistsAndNoConflicts_returnedSaveVisits() {
        Long visitId = 1L;
        Long patientId = 1L;
        Patient patient = new Patient(1L, "Adam", "nowak", "gmail.com", "hasło", "1", "2", "1990-01-01");
        Visit visit = new Visit();
        visit.setStartDateTime(LocalDateTime.of(2030,1,1,10,0));
        visit.setEndDateTime(LocalDateTime.of(2030,1,1,10,15));

        Visit savedVisit = new Visit();
        savedVisit.setId(visitId);
        savedVisit.setStartDateTime(visit.getStartDateTime());
        savedVisit.setStartDateTime(visit.getEndDateTime());
        savedVisit.setPatient(patient);

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(visitRepository.save(visit)).thenReturn(savedVisit);

        VisitDTO result = visitService.bookVisit(visitId, patientId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals(1L, result.patient().id()),
                () -> Assertions.assertEquals("Adam", result.patient().firstName())
        );
    }

    @Test
    void bookVisit_VisitNotFound_returnedException(){
        Long visitId = 1L;
        Long patientId = 1L;
        when(visitRepository.findById(visitId)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> visitService.bookVisit(visitId,patientId));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Visit not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void bookVisit_PatientNotFound_returnedException(){
        Long visitId = 1L;
        Long patientId = 1L;
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setStartDateTime(LocalDateTime.of(2030,1,1,10,0));
        visit.setEndDateTime(LocalDateTime.of(2030,1,1,10,15));

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> visitService.bookVisit(visitId,patientId));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void getVisitsForPatient_PatientExists_ReturnedVisits(){
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "Adam", "nowak", "gmail.com", "hasło", "1", "2", "1990-01-01");
        Visit visit1 = new Visit();
        visit1.setId(10L);
        visit1.setPatient(patient);
        visit1.setStartDateTime(LocalDateTime.of(2030, 1, 1, 10, 0));
        visit1.setEndDateTime(LocalDateTime.of(2030, 1, 1, 10, 15));

        Visit visit2 = new Visit();
        visit2.setId(11L);
        visit2.setPatient(patient);
        visit2.setStartDateTime(LocalDateTime.of(2030, 1, 2, 10, 0));
        visit2.setEndDateTime(LocalDateTime.of(2030, 1, 2, 10, 15));

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(visitRepository.findByPatient(patient)).thenReturn(List.of(visit1,visit2));

        List<VisitDTO> result = visitService.getVisitsForPatient(patientId);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.size()),
                () -> Assertions.assertEquals(10L, result.get(0).id()),
                () -> Assertions.assertEquals(11L, result.get(1).id()),
                () -> Assertions.assertEquals(1L, result.get(0).patient().id())
        );
    }

    @Test
    void getVisitsForPatient_PatientNotExists_ReturnedException(){
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () ->visitService.getVisitsForPatient(patientId));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }
}
*/
