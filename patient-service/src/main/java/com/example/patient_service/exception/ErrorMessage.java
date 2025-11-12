package com.example.patient_service.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@AllArgsConstructor
public class ErrorMessage {
    public String message;
    public HttpStatus httpStatus;
    public OffsetDateTime dateTime;
}
