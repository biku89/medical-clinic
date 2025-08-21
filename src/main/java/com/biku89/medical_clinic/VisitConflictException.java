package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class VisitConflictException extends MedicalClinicException {
    public VisitConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
