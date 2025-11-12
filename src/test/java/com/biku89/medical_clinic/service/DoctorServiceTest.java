/*
package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DoctorServiceTest {
    DoctorService doctorService;
    DoctorRepository doctorRepository;
    DoctorMapper doctorMapper;
    InstitutionRepository institutionRepository;

    @BeforeEach
    void setUp() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorService = new DoctorService(this.doctorRepository, this.doctorMapper, this.institutionRepository);
    }

    @Test
    void getAllDoctors_testPageable_returnDoctors() {
        //given
        Pageable pageable = PageRequest.of(0, 5);
        List<Doctor> doctors = new ArrayList<>();
        List<Institution> institutions = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "hasło", institutions);
        Doctor doctor1 = new Doctor(2L, "Adam", "Nowak", "adamNowak@gmail.com", "hasło1", institutions);
        doctors.add(doctor);
        doctors.add(doctor1);
        Page<Doctor> page = new PageImpl<>(doctors);
        when(doctorRepository.findAll(pageable)).thenReturn(page);
        //when
        PageDTO<DoctorDTO> result = doctorService.getAllDoctors(pageable);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.getContent().size()),
                () -> Assertions.assertEquals("Jan", result.getContent().getFirst().firstName()),
                () -> Assertions.assertEquals("Adam", result.getContent().get(1).firstName()),
                () -> Assertions.assertEquals("Kowalski", result.getContent().getFirst().lastName()),
                () -> Assertions.assertEquals("jankowalski@gmail.com", result.getContent().getFirst().email()),
                () -> Assertions.assertEquals("adamNowak@gmail.com", result.getContent().get(1).email())
        );
    }

    @Test
    void getDoctorByEmail_DoctorExists_returnedDoctor() {
        //given
        List<Institution> institutions = new ArrayList<>();
        String email = "jankowalski@gmail.com";
        Doctor doctor = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutions);
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));
        //when
        DoctorDTO result = doctorService.getDoctorByEmail(email);
        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("Jan", result.firstName()),
                () -> Assertions.assertEquals("Kowalski", result.lastName()),
                () -> Assertions.assertEquals(email, result.email())
        );
    }

    @Test
    void getDoctorByEmail_DoctorNotExists_ReturnedException() {
        String email = "jankowalski@gmail.com";
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());
        //when+then
        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorByEmail(email));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void addDoctor_doctorNotExists_returnedDoctor() {
        List<Institution> institutionsEntity = new ArrayList<>();
        List<InstitutionSimpleDTO> institutions = new ArrayList<>();
        String email = "jankowalski@gmail.com";
        Doctor doctorEntity = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutionsEntity);
        DoctorDTO doctor = new DoctorDTO(1L, "Jan", "Kowalski", email, institutions);
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(doctorRepository.save(doctorEntity)).thenReturn(doctorEntity);

        DoctorDTO result = doctorService.addDoctor(doctor);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("Jan", result.firstName()),
                () -> Assertions.assertEquals("Kowalski", result.lastName()),
                () -> Assertions.assertEquals(email, result.email())
        );
    }

    @Test
    void addDoctor_DoctorExists_returnedException() {
        String email = "jankowalski@gmail.com";
        List<InstitutionSimpleDTO> institutions = new ArrayList<>();
        List<Institution> institutionsEntity = new ArrayList<>();
        Doctor doctorEntity = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutionsEntity);
        DoctorDTO doctor = new DoctorDTO(1L, "Jan", "Kowalski", email, institutions);
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctorEntity));

        EmailExistingException exception = Assertions.assertThrows(EmailExistingException.class, () -> doctorService.addDoctor(doctor));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor with this email already exists", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus())

        );
    }

    @Test
    void updateDoctor_DoctorExists_returnedUpdatedDoctor() {
        String email = "jankowalski@gmail.com";
        List<Institution> institutionsEntity = new ArrayList<>();
        Doctor doctorExists = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutionsEntity);
        DoctorUpdateCommand doctorUpdateCommand = new DoctorUpdateCommand(1L, "Adam", "nowak", "adamnowak@gmail.com", "NoweHasło");
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctorExists));
        when(doctorRepository.save(doctorExists)).thenReturn(doctorExists);

        DoctorDTO result = doctorService.updateDoctor(email, doctorUpdateCommand);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Adam", result.firstName()),
                () -> Assertions.assertEquals("nowak", result.lastName()),
                () -> Assertions.assertEquals("adamnowak@gmail.com", result.email())
        );
    }

    @Test
    void updateDoctor_DoctorNotFound_ReturnedException() {
        String email = "jankowalski@gmail.com";
        DoctorUpdateCommand doctorUpdateCommand = new DoctorUpdateCommand(1L, "Adam", "nowak", email, "NoweHasło");
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor(email, doctorUpdateCommand));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void deleteDoctorByEmail_DoctorExists_DeletedDoctor() {
        String email = "jankowalski@gmail.com";
        List<Institution> institutionsEntity = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutionsEntity);
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctorByEmail(email);

        verify(doctorRepository, times(1)).delete(doctor);
    }

    @Test
    void deleteDoctorByEmail_DoctorNotFound_returnedException() {
        String email = "jankowalski@gmail.com";
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctorByEmail(email));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void assignClinic_DoctorAndInstitutionExists_ReturnedAddInstitutionsToDoctor() {
        String email = "jankowalski@gmail.com";
        String name = "Szpital";
        List<Doctor> doctors = new ArrayList<>();
        Institution institution = new Institution(1L, name, "Łódź", "0", "1", "2", doctors);
        List<Institution> institutionsEntity = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "Jan", "Kowalski", email, "hasło", institutionsEntity);
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName(name)).thenReturn(Optional.of(institution));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Institution result = doctorService.assignClinic(email, name);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Szpital", result.getName()),
                () -> Assertions.assertEquals("Łódź", result.getCity()),
                () -> Assertions.assertEquals(name, doctor.getInstitutions().getFirst().getName()),
                () -> Assertions.assertEquals("Łódź", doctor.getInstitutions().getFirst().getCity())
        );
    }

    @Test
    void AssignClinic_DoctorNotFound_ReturnedException() {
        String email = "jankowalski@gmail.com";
        String name = "Szpital";
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.assignClinic(email, name));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Doctor not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void AssignClinic_InstitutionNotFound_ReturnedException() {
        String email = "jankowalski@gmail.com";
        String name = "Szpital";
        Doctor doctor = new Doctor(1L, "jan", "kowalski", email, "hasło", new ArrayList<>());
        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName(name)).thenReturn(Optional.empty());

        InstitutionNotFoundException exception = Assertions.assertThrows(InstitutionNotFoundException.class, () -> doctorService.assignClinic(email, name));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Institution not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }
}
*/
