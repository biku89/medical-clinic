package com.biku89.medical_clinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //lombok tworzy konstruktor prywatny, final przy klasie oznacza że nie pozwalam na dziedziczenie
public final class PatientValidator {

    public static void modifyingIdCardNoNotAllowed(Patient existingPatient, Patient updatedPatient) {
        if (updatedPatient.getIdCardNo() != null && !existingPatient.getIdCardNo().equals(updatedPatient.getIdCardNo())) {
            throw new IdNumberModificationException("Modifying ID card number is not allowed");
        }
    }

    public static void notAllowedChangeToNull(Patient updatedPatient) {
        if (updatedPatient.getFirstName() == null ||
                updatedPatient.getLastName() == null ||
                updatedPatient.getBirthday() == null ||
                updatedPatient.getPassword() == null ||
                updatedPatient.getEmail() == null ||
                updatedPatient.getPhoneNumber() == null) {
            throw new NotAllowedNullExpception("Setting required fields to null is not allowed");
        }
    }

    public static void patientWithEmailIsAlreadyExist(PatientRepository patientWithSameEmail, Patient updatedPatient, String email) {
        if (patientWithSameEmail.findByEmail(updatedPatient.getEmail()).isPresent() && !email.equals(updatedPatient.getEmail())) {
            throw new EmailExistingException("patient with this email email already exists");
        }
    }
}
