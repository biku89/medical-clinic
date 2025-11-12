package com.example.patient_service.model;

import java.util.List;

public record DoctorDTO(Long id, String firstName, String lastName, String email, List<InstitutionSimpleDTO> institutions, DoctorSpecialization doctorSpecialization) {
}
