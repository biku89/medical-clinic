package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class InstitutionExistingException extends MedicalClinicException {
    public InstitutionExistingException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
