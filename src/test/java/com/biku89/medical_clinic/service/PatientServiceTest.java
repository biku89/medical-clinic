package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
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
public class PatientServiceTest {
    PatientService patientService;
    PatientRepository patientRepository;
    PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class); // tutaj jest prawdziwy maper nie mock
        this.patientService = new PatientService(this.patientRepository, this.patientMapper);
    }

    @Test
    void getPateints_testPageble_returnPatients() {
        // given -> przygotowanie danych potrzebnych do przeprowadzenia testu. Co mocki mają zwrócić
        Pageable pageable = PageRequest.of(0, 5);
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "hasło", "10", "000", "1900-0101");
        Patient patient1 = new Patient(2L, "Adam", "Nowak", "adamNowak@gmail.com", "hasło", "0", "1111", "200-01-01");
        patients.add(patient);
        patients.add(patient1);
        Page<Patient> page = new PageImpl<>(patients);
        when(patientRepository.findAll(pageable)).thenReturn(page);
        //Tutaj nie robię when -> then dla mappera bo już wyżej jest prawdzwidy
        //when -> mówimy co ma wykonać test
        PageDTO<PatientDTO> result = patientService.getPatients(pageable);

        //then -> weryfikacja testu
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.getContent().size()),
                () -> Assertions.assertEquals("Jan", result.getContent().getFirst().firstName()),
                () -> Assertions.assertEquals("Adam", result.getContent().get(1).firstName()),
                () -> Assertions.assertEquals("jankowalski@gmail.com", result.getContent().getFirst().email())
                //Dokończ więcej testów
        );
    }

    @Test
    void getPatientByEmail_PatientExists_PatientReturned() {
        //given -> robię przygotowanie danych
        Patient patient = new Patient(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "hasło", "10", "000", "1900-0101");
        when(patientRepository.findByEmail("jankowalski@gmail.com")).thenReturn(Optional.of(patient));
        //when -> wykonuje test
        PatientDTO result = patientService.getPatientByEmail("jankowalski@gmail.com");
        //Then -> Dokonuje weryfikacji testu
        Assertions.assertAll(
                () -> Assertions.assertEquals("Jan", result.firstName()),
                () -> Assertions.assertEquals("Kowalski", result.lastName()),
                () -> Assertions.assertEquals("jankowalski@gmail.com", result.email()),
                () -> Assertions.assertEquals("000", result.phoneNumber()),
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("1900-0101", result.birthday())
        );
    }

    @Test
    void addPatient_PatientsExists_PatientSaveAndReturned() {
        //given - przygotować dane
        PatientDTO patientDTO = new PatientDTO(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "000", "01-01-1000");
        Patient patientEntity = patientMapper.toEntity(patientDTO);
        when(patientRepository.findByEmail("jankowalski@gmail.com")).thenReturn(Optional.empty());
        when(patientRepository.save(patientEntity)).thenReturn(patientEntity);
        //when wykonuje test
        PatientDTO result = patientService.addPatient(patientDTO);
        //then - weryfikacja
        Assertions.assertAll(
                () -> Assertions.assertEquals("Jan", result.firstName()),
                () -> Assertions.assertEquals("Kowalski", result.lastName()),
                () -> Assertions.assertEquals("jankowalski@gmail.com", result.email()),
                () -> Assertions.assertEquals("000", result.phoneNumber()),
                () -> Assertions.assertEquals(1L, result.id()),
                () -> Assertions.assertEquals("01-01-1000", result.birthday())
        );
    }

    @Test
    void updatePatient_PatientExists_PatientUpdatedAndReturned() {
        //given -> przygotowanie danych
        String email = "jankowalski@gmail.com";
        Patient existingPatient = new Patient(1L, "jan", "kowalski", email, "hasło", "2", "0", "01-10-100");
        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand("adam", "nowak", "adamnowak@gmail.com", "s", "2", "0", "11");
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        //when
        PatientDTO result = patientService.updatePatient(email, patientUpdateCommand);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("adam", result.firstName()),
                () -> Assertions.assertEquals("nowak", result.lastName()),
                () -> Assertions.assertEquals("0", result.phoneNumber())
        );
    }

    @Test
    void updatePassword_patientExists_PatientUpdatedAndReturned() {
        //given
        String email = "jankowlaski@gmail.com";
        Patient patient = new Patient(1L, "jan", "kowalski", email, "hasło", "2", "0", "01-10-100");
        String updatePassword = "noweHasło";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        //when
        PatientDTO result = patientService.updatePassword(email, updatePassword);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("noweHasło", patient.getPassword()),
                () -> Assertions.assertEquals("jan", result.firstName()),
                () -> Assertions.assertEquals("kowalski", result.lastName()),
                () -> Assertions.assertEquals(1L, result.id())
        );
    }

    @Test
    void deletePatientByEmail_PatientExists_PatientDeleted() {
        //given
        String email = "jankowalski@gmail.com";
        Patient patient = new Patient(1L, "jan", "kowalski", email, "hasło", "2", "0", "01-10-100");
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        //when
        patientService.deleteByEmail(email);
        //then
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    void getPatientByEmail_PatientNotFound_returnedException() {
        //given
        Patient patient = new Patient(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "hasło", "10", "000", "1900-0101");
        when(patientRepository.findByEmail("jankowalski@gmail.com")).thenReturn(Optional.empty());
        //When and then
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByEmail("jankowalski@gmail.com"));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient Not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void addPatient_PatientsExists_returnedException() {
        //given
        Patient patient = new Patient(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "hasło", "10", "000", "1900-0101");
        PatientDTO patientDTO = new PatientDTO(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "000", "01-01-1000");
        when(patientRepository.findByEmail("jankowalski@gmail.com")).thenReturn(Optional.of(patient));
        //when and then
        EmailExistingException exception = Assertions.assertThrows(EmailExistingException.class, () -> patientService.addPatient(patientDTO));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient with this email already exists", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus())
        );
    }

    @Test
    void updatePatient_PatientNotFound_returnedException() {
        //given
        String email = "jankowalski@gmail.com";
        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand("adam", "nowak", "adamnowak@gmail.com", "s", "2", "0", "11");
        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());
        //when and then
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(email, patientUpdateCommand));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }

    @Test
    void deletePatients_PatientExists_PatientsDeleted() {
        //given
        List<Long> idsToDelete = List.of(1L, 2L);
        PatientsDeleteCommand patientsDeleteCommand = new PatientsDeleteCommand();
        patientsDeleteCommand.setIds(idsToDelete);

        //when
        patientService.deletePatients(patientsDeleteCommand);

        //then
        verify(patientRepository, times(1)).deleteAllById(idsToDelete);
    }

    @Test
    void deleteByEmail_PatientNotExists_ReturnedException() {
        //given
        String email = "jankowalski@gmail.com";
        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when + then
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.deleteByEmail(email));

        Assertions.assertAll(
                () -> Assertions.assertEquals("Patient not found", exception.getMessage()),
                () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus())
        );
    }
}
