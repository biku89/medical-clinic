package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class MedicalClinicException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    }

