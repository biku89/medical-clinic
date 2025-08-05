package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public record PatientDTO(Long id, String firstName, String lastName, String email, String phoneNumber, String birthday) {
}
