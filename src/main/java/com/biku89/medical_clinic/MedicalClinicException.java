package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
public class MedicalClinicException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MedicalClinicException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}

