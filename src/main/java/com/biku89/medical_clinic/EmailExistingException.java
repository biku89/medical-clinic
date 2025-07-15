package com.biku89.medical_clinic;

public class EmailExistingException extends RuntimeException {
    public EmailExistingException(String message) {
        super(message);
    }
}
