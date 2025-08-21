package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class DoctorNotFoundException extends MedicalClinicException {
    public DoctorNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
