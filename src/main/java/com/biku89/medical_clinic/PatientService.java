package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientByEmail(String email){
        return patientRepository.findByEmail(email);
    }

    public Patient addPatient(Patient patient){
        patientRepository.addPatient(patient);
        return patient;
    }

    public Optional<Patient> updatePatient(String email, Patient updatedPatient) {
        return patientRepository.updatePatientByEmail(email, updatedPatient);
    }

    public boolean deleteByEmail(String email){
        return patientRepository.deleteByEmail(email);
    }
}

