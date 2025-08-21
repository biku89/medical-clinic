package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class IncorrectDataException extends MedicalClinicException {
  public IncorrectDataException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
