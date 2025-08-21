package com.biku89.medical_clinic;

import java.util.List;

public record DoctorDTO(Long id, String firstName, String lastName, String email, List<InstitutionSimpleDTO> institutions) {

}
