package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class IstitutionExistingException extends MedicalClinicException {
    public IstitutionExistingException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
