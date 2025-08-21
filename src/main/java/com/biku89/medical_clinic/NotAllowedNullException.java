package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class NotAllowedNullException extends MedicalClinicException {
    public NotAllowedNullException(String message) {
        super("Setting fields to null is not allowed", HttpStatus.CONFLICT);
    }
}
