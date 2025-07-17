package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    //private final PatientValidator patientValidator;

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient addPatient(Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()){
            throw new EmailExistingException("Już istnieje pacjent z takim mailem");
        }
        patientRepository.addPatient(patient);
        return patient;
    }

    public Optional<Patient> updatePatient(String email, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        PatientValidator.modifyingIdCardNoNotAllowed(existingPatient, updatedPatient);
        PatientValidator.notAllowedChangeToNull(updatedPatient);

        Optional<Patient> patientWithSameEmail = patientRepository.findByEmail(updatedPatient.getEmail());
        PatientValidator.patientWithEmailIsAlreadyExist(patientWithSameEmail,updatedPatient,email);

        return patientRepository.updatePatientByEmail(email, updatedPatient);
    }

    public Patient updatePassword(String email, String updatePassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found."));

        return patientRepository.updatePassword(patient, updatePassword)
                .orElseThrow(() -> new RuntimeException("Failed to update password."));
    }

    public boolean deleteByEmail(String email) {
        return patientRepository.deleteByEmail(email);
    }
}

