package com.biku89.medical_clinic;

import lombok.Getter;
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
@Getter
@RestControllerAdvice
public class GlobalExceptionHandler {

    //ex jest to parametr który przekazuje wyjątek rzucony serwisie, webRequest zawiera dane o kontekście żądań http(nagłówki itd.)

    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ErrorMessage> handleMedicalClinicException(MedicalClinicException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), OffsetDateTime.now());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorMessage);
    }
}
