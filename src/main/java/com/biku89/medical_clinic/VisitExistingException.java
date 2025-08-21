package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class VisitExistingException extends MedicalClinicException {
    public VisitExistingException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
