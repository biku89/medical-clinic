package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class IdNumberModificationException extends MedicalClinicException {
    public IdNumberModificationException(String message) {
        super("Modifying ID card number is not allowed", HttpStatus.CONFLICT);
    }
}
