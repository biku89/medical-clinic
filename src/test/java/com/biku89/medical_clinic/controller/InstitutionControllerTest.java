package com.biku89.medical_clinic.controller;

import com.biku89.medical_clinic.Institution;
import com.biku89.medical_clinic.InstitutionDTO;
import com.biku89.medical_clinic.InstitutionService;
import com.biku89.medical_clinic.PageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
public class InstitutionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    InstitutionService institutionService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllInstitutions_ReturnInstitutionPage() throws Exception {
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, "Szpital", "łódź", "0", "1", "2", new ArrayList<>());
        PageImpl<InstitutionDTO> page = new PageImpl<>(List.of(institutionDTO), PageRequest.of(0, 1), 1);
        PageDTO<InstitutionDTO> pageDTO = new PageDTO<>(page);
        when(institutionService.getAllInstitutions(any())).thenReturn(pageDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.content[0].name").value("Szpital"),
                        jsonPath("$.content[0].city").value("łódź"));
    }

    @Test
    void getInstitutionsById_InstitutionsExists_returnedInstitutions() throws Exception {
        Long id = 1L;
        InstitutionDTO institutionDTO = new InstitutionDTO(id, "Szpital", "łódź", "0", "1", "2", new ArrayList<>());
        when(institutionService.getInstitutionsById(id)).thenReturn(institutionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/institutions/1").contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.name").value("Szpital")
                );
    }

    @Test
    void addInstitution_InstitutionExists_saveInstitution() throws Exception{
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, "Szpital", "łódź", "0", "1", "2", new ArrayList<>());

        when(institutionService.addInstitution(institutionDTO)).thenReturn(institutionDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/institutions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(institutionDTO)))
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.name").value("Szpital"),
                        jsonPath("$.city").value("łódź")
                );
    }

    @Test
    void deleteInstitutionByName_InstitutionExists_DeletedInstitution()throws Exception{
        String name = "szpital";

        mockMvc.perform(MockMvcRequestBuilders.delete("/institutions/szpital"))
                .andExpectAll(
                        status().isOk()
                );
        verify(institutionService, times(1)).deleteInstitution(name);
    }
}

