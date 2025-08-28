package com.biku89.medical_clinic.service;

import com.biku89.medical_clinic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PatientServiceTest {
    PatientService patientService;
    PatientRepository patientRepository;
    PatientMapper patientMapper;

    @BeforeEach
    void setUp(){
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class); // tutaj jest prawdziwy maper nie mock
        this.patientService = new PatientService(this.patientRepository, this.patientMapper);
    }

    @Test
    void getPateints_testPageble_returnPatients(){
        // given -> przygotowanie danych potrzebnych do przeprowadzenia testu. Co mocki mają zwrócić
        Pageable pageable = PageRequest.of(0,5);
        List<Patient> patients = new ArrayList<>();
        Patient patient =new Patient(1L,"Jan","Kowalski","jankowalski@gmail.com","hasło", "10","000","1900-0101");
        Patient patient1 = new Patient(2L, "Adam","Nowak", "adamNowak@gmail.com", "hasło", "0", "1111", "200-01-01");
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
    void addPatient_PatientsExists_PatientSaveAndReturned(){
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
    void updatePatient_PatientExists_PatientUpdatedAndReturned(){
        //given -> przygotowanie danych
        String email = "jankowalski@gmail.com";
        Patient existingPatient = new Patient(1L, "jan","kowalski", email, "hasło","2","0","01-10-100");
        PatientUpdateCommand patientUpdateCommand = new PatientUpdateCommand();
        patientUpdateCommand.setFirstName("adam");
        patientUpdateCommand.setLastName("nowak");
        patientUpdateCommand.setPhoneNumber("0");
        patientUpdateCommand.setPassword("s");
        patientUpdateCommand.setBirthday("11");
        patientUpdateCommand.setEmail("adamnowak@gmail.com");
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
    void updatePassword_patientExists_PatientUpdatedAndReturned(){
        //given
        String email = "jankowlaski@gmail.com";
        Patient patient = new Patient(1L, "jan","kowalski", email, "hasło","2","0","01-10-100");
        String updatePassword = "noweHasło";
        patient.setPassword(updatePassword);
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
    void deletePatientByEmail_PatientExists_PatientDeleted(){
        //given
        String email = "jankowalski@gmail.com";
        Patient patient = new Patient(1L, "jan","kowalski", email, "hasło","2","0","01-10-100");
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        //when
        patientService.deleteByEmail(email);
        //then ??
    }
}
