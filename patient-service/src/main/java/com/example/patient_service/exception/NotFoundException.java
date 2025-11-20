package com.example.patient_service.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends PatientServiceException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
