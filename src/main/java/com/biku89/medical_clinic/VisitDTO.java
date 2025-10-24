package com.biku89.medical_clinic;

import java.time.LocalDateTime;

public record VisitDTO(Long id, LocalDateTime startDateTime,LocalDateTime endDateTime, DoctorDTO doctor, PatientDTO patient) {
}
