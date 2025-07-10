package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    Optional<Patient> getPatientByEmail(String email){
        return patientRepository.findByEmail(email);
    }



    public Patient addPatient(Patient patient){
        patientRepository.addPatient(patient);
        return patient;
    }

    Optional<Patient> updatePatient(String email, Patient updatedPatient) {
        Optional<Patient> existingPateint = patientRepository.findByEmail(email);
        existingPateint.ifPresent(patient -> {
            patient.setFistName(updatedPatient.getFistName());
            patient.setLastName(updatedPatient.getLastName());
            patient.setEmail(updatedPatient.getEmail());
            patient.setPassword(updatedPatient.getEmail());
            patient.setIdCardNo(updatedPatient.getIdCardNo());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
            patient.setBirthday(updatedPatient.getBirthday());
        });
        return existingPateint;
    }

    public boolean deleteByEmail(String email){
        return patientRepository.deleteByEmail(email);
    }
}

