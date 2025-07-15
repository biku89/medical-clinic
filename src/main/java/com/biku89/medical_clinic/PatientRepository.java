package com.biku89.medical_clinic;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class PatientRepository {
    private List<Patient> patients = new ArrayList<>();

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        if (findByEmail(patient.getEmail()).isPresent()){
            throw new EmailExistingException("Pacjent już istnieje");
        }
        patients.add(patient);
        return patient;
    }

    public Optional<Patient> updatePatientByEmail(String email, Patient updatedPatient) {
        Optional<Patient> existingPatient = findByEmail(email);
        existingPatient.ifPresent(patient -> {
            patient.setFirstName(updatedPatient.getFirstName());
            patient.setLastName(updatedPatient.getLastName());
            patient.setEmail(updatedPatient.getEmail());
            patient.setPassword(updatedPatient.getPassword());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
            patient.setBirthday(updatedPatient.getBirthday());
        });
        return existingPatient;
    }

    public Optional<Patient> updatePassword(String email, String updatedPassword) {
        Optional<Patient> existingPatient = findByEmail(email);
        existingPatient.ifPresent(patient -> {
            patient.setPassword(updatedPassword);
        });
        return existingPatient;
    }

    public Optional<Patient> updatePassword(Patient patient, String updatedPassword) {
        if (findByEmail(patient.getEmail()).isEmpty()) {
            return Optional.empty(); // sytuacje kiedy nie ma pacjenta.
        }
        //Sytuacja kiedy pacjent istnieje
        patient.setPassword(updatedPassword);
        return Optional.of(patient);
    }

    public boolean deleteByEmail(String email) {
        return patients.removeIf(patient -> patient.getEmail().equals(email));
    }
}
