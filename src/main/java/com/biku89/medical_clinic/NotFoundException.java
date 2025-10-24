package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MedicalClinicException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
