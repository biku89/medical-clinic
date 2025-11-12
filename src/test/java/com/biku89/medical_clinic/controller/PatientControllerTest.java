/*
package com.biku89.medical_clinic.controller;

import com.biku89.medical_clinic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    PatientService patientService;

    @Test
    void getPatients_returnPagePatients() throws Exception {
        PatientDTO patient = new PatientDTO(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "000", "1900-01-01");

        PageImpl<PatientDTO> page = new PageImpl<>(
                List.of(patient),
                PageRequest.of(0, 1), 1
        );
        PageDTO<PatientDTO> pageDTO = new PageDTO<>(page);

        when(patientService.getPatients(any())).thenReturn(pageDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content[0].firstName").value("Jan"),
                        jsonPath("$.content[0].lastName").value("Kowalski"),
                        jsonPath("$.content[0].email").value("jankowalski@gmail.com"),
                        jsonPath("$.totalPages").value(1));
    }

    @Test
    void getPatientByEmail_PatientExists_returnPatient() throws Exception {
        //given
        String email = "jankowalski@gmail.com";
        PatientDTO patient = new PatientDTO(1L, "Jan", "Kowalski", email, "000", "1900-01-01");
        when(patientService.getPatientByEmail(email)).thenReturn(patient);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/jankowalski@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski"),
                        jsonPath("$.email").value("jankowalski@gmail.com"));

    }

    @Test
    void addPatient_PatientExists_returnAddPatient() throws Exception {
        PatientDTO patient = new PatientDTO(1L, "Jan", "Kowalski", "jankowalski@gmail.com", "000", "1900-01-01");

        when(patientService.addPatient(patient)).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(patient)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1),
                        jsonPath("$.firstName").value("Jan")
                );
    }

    @Test
    void updatePatient_PatientExist_returnUpdatedPatient() throws Exception {
        String email = "jankowalski@gmail.com";
        PatientDTO updatedPatient = new PatientDTO(1L, "Jan", "Kowalski", email, "000", "1900-01-01");
        PatientUpdateCommand updateCommand = new PatientUpdateCommand("Jan", "Kowalski", email, "hasło", "3", "000", "1900-01-01");

        when(patientService.updatePatient(email, updateCommand)).thenReturn(updatedPatient);

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/jankowalski@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateCommand)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski")
                );
    }

    @Test
    void deletePatient_PatientExists_deletedPatient() throws Exception {
        String email = "jankowalski@gmail.com";

        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/jankowalski@gmail.com"))
                .andExpectAll(
                        status().is(204)
                );
        verify(patientService, times(1)).deleteByEmail(email);
    }

    @Test
    void patientUpdatePassword_PatientExists_returnedUpdatePassword() throws Exception {
        String email = "jankowalski@gmail.com";
        String updatePassowrd = "hasłopoupdate";
        PatientDTO updatedPatient = new PatientDTO(1L, "Jan", "Kowalski", email, "00", "1990-01-01");

        when(patientService.updatePassword(email, updatePassowrd)).thenReturn(updatedPatient);

        Patient patientWithNewPassword = new Patient();
        patientWithNewPassword.setPassword(updatePassowrd);

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/jankowalski@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientWithNewPassword)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski")
                );
    }

    @Test
    void deletePatients_PatientsExists_DeletedPatients() throws Exception {
        PatientsDeleteCommand patientsDeleteCommand = new PatientsDeleteCommand();
        patientsDeleteCommand.setIds(List.of(1L, 2L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientsDeleteCommand)))
                .andExpectAll(
                        status().is(200)
                );
        verify(patientService, times(1)).deletePatients(patientsDeleteCommand);
    }
}
*/
