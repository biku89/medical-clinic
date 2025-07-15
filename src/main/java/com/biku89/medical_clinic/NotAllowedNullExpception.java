package com.biku89.medical_clinic;

public class NotAllowedNullExpception extends RuntimeException {
    public NotAllowedNullExpception(String message) {
        super(message);
    }
}
