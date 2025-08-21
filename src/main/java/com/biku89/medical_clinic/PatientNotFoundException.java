package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalClinicException {
    public PatientNotFoundException(String message) {
        super("Patient not found", HttpStatus.NOT_FOUND);
    }
}
