package com.biku89.medical_clinic;

import org.springframework.http.HttpStatus;

public class InstitutionNotFoundException extends MedicalClinicException {
  public InstitutionNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
