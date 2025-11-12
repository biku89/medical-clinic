/*
package com.biku89.medical_clinic.controller;

import com.biku89.medical_clinic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    VisitService visitService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createVisit_VisitExists_returnedVisit() throws Exception {
        Long doctorId = 1L;
        LocalDateTime start = LocalDateTime.of(2030, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2030, 1, 1, 10, 15);
        CreateVisitCommand command = new CreateVisitCommand(start, end);

        VisitDTO visitDTO = new VisitDTO(1L, start, end, null, null);
        when(visitService.createVisit(doctorId, command)).thenReturn(visitDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/visits/doctor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.startDateTime").value("2030-01-01T10:00:00"),
                        jsonPath("$.endDateTime").value("2030-01-01T10:15:00"));
    }

    @Test
    void bookVisit_VisitAndPatientExists_ReturnedBookedVisit() throws Exception {
        Long visitId = 1L;
        Long patientId = 1L;

        LocalDateTime start = LocalDateTime.of(2030, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2030, 1, 1, 10, 15);
        PatientDTO patientDTO = new PatientDTO(patientId, "Adam", "nowak", "adamnowak@gmail.com", "0", "1900-00-00");

        VisitDTO visitDTO = new VisitDTO(visitId, start, end, null, patientDTO);
        when(visitService.bookVisit(visitId, patientId)).thenReturn(visitDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/visits/1/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.patient.id").value(1L),
                        jsonPath("$.startDateTime").value("2030-01-01T10:00:00"),
                        jsonPath("$.endDateTime").value("2030-01-01T10:15:00"),
                        jsonPath("$.patient.firstName").value("Adam"));
    }
}
*/
