package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class EmailExistingException extends MedicalClinicException {
    public EmailExistingException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
