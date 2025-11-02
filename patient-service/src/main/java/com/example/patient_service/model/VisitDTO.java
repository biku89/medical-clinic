package com.example.patient_service.model;

import lombok.Data;

import java.time.LocalDateTime;

public record VisitDTO(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, DoctorDTO doctor, PatientDTO patient) {
}
