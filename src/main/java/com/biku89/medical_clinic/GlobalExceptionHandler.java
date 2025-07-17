package com.biku89.medical_clinic;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

 /*   @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<ErrorMessage> handlePatientNotFound(PatientNotFoundException ex) {
        //PatientNotFoundException ex jest to parametr który przekazuje wyjątek rzucony serwisie, webRequest zawiera dane o kontekście żądań http(nagłówki itd.)
        ErrorMessage errorMessage = new ErrorMessage("Patient not found", HttpStatus.NOT_FOUND, OffsetDateTime.now());
        return ResponseEntity.status(404).body(errorMessage);
    }
*/
    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ErrorMessage> handleMedicalClinicException(MedicalClinicException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), OffsetDateTime.now());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorMessage);
    }
}
    /*@ExceptionHandler(EmailExistingException.class)
    ResponseEntity<Object> handleEmailExisting(EmailExistingException ex, WebRequest request) {
        String bodyOfResponse = ("Email already exists");
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(IdNumberModificationException.class)
    ResponseEntity<Object> handleIdNumberModification(IdNumberModificationException ex, WebRequest request) {
        String bodyOfResponse = ("Modification of ID card number is not allowed");
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(NotAllowedNullExpception.class)
    ResponseEntity<Object> handleNotAllowedNull(NotAllowedNullExpception ex, WebRequest request) {
        String bodyOfResponse = ("Setting fields to null is not allowed");
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}*/
