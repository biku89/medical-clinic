package com.biku89.medical_clinic;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class PatientRepository {
    private List<Patient> patients = new ArrayList<>();

    List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    Optional<Patient> findByEmail(String email){
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email)).findFirst();
    }

    public Patient addPatient(Patient patient){
        patients.add(patient);
        return patient;
    }

    public boolean deleteByEmail(String email){
        return patients.removeIf(patient -> patient.getEmail().equals(email));
    }
}
