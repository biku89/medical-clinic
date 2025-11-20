package com.example.patient_service.model;

public record PatientDTO(Long id, String firstName, String lastName, String email, String phoneNumber, String birthday) {
}
