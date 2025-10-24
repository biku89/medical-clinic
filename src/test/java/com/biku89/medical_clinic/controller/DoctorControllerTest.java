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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    DoctorService doctorService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllDoctors_returnDoctorPage() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Jan", "Kowalski", "jankowalski@gmail.com", new ArrayList<>());

        PageImpl<DoctorDTO> page = new PageImpl<>(List.of(doctorDTO), PageRequest.of(0, 1), 1);
        PageDTO<DoctorDTO> pageDTO = new PageDTO<>(page);

        when(doctorService.getAllDoctors(any())).thenReturn(pageDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.content[0].firstName").value("Jan"),
                        jsonPath("$.content[0].lastName").value("Kowalski"),
                        jsonPath("$.content[0].email").value("jankowalski@gmail.com"),
                        jsonPath("$.totalPages").value(1));
    }
    @Test
    void getDoctorByEmail_DoctorExists_ReturnedDoctor() throws Exception{
        String email = "jankowalski@gmail.com";
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Jan", "Kowalski",email, new ArrayList<>());

        when(doctorService.getDoctorByEmail(email)).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/jankowalski@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski"),
                        jsonPath("$.email").value("jankowalski@gmail.com")
                );
    }
    @Test
    void addDoctor_DoctorExists_ReturnedSaveDoctor() throws Exception{
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Jan", "Kowalski","jankowalski@gmail.com", new ArrayList<>());
        when(doctorService.addDoctor(doctorDTO)).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorDTO)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski"),
                        jsonPath("$.email").value("jankowalski@gmail.com")
                );
    }
    @Test
    void updateDoctor_DoctorExists_returnedUpdateDoctor() throws Exception{
        String email = "jankowalski@gmail.com";
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Jan", "Kowalski",email, new ArrayList<>());
        DoctorUpdateCommand doctorUpdateCommand = new DoctorUpdateCommand(1L,"Jan","Kowalski",email,"hasło");

        when(doctorService.updateDoctor(email,doctorUpdateCommand)).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctors/jankowalski@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorUpdateCommand)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.firstName").value("Jan"),
                        jsonPath("$.lastName").value("Kowalski"),
                        jsonPath("$.email").value("jankowalski@gmail.com"));
    }
    @Test
    void deleteDoctorByEmail_DoctorExists_DoctorDeleted() throws Exception{
        String email = "jankowalski@gmail.com";

        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/jankowalski@gmail.com"))
                .andExpectAll(
                        status().is(200)
        );
        verify(doctorService,times(1)).deleteDoctorByEmail(email);
    }

    @Test
    void assignInstitutionToDoctor_DoctorExists_InstitutionAssignedToDoctor()throws Exception{
        String email = "jankowalski@gmail.com";
        String name = "Szpital";
        InstitutionAssignRequest institutionAssign = new InstitutionAssignRequest(name);
        Institution institution = new Institution(1L,name,"łódź","000","a","1",new ArrayList<>());

        when(doctorService.assignClinic(email, name)).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctors/jankowalski@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(institutionAssign)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("Szpital")
                );
    }

}
