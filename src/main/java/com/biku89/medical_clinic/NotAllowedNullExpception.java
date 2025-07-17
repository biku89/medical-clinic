package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class NotAllowedNullExpception extends MedicalClinicException {
    public NotAllowedNullExpception(String message) {
        super("Setting fields to null is not allowed", HttpStatus.CONFLICT);
    }
}
