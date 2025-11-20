package com.example.patient_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatientServiceException extends RuntimeException {
  private final HttpStatus httpStatus;

    public PatientServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
