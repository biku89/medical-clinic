package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

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
        Optional<Patient> existingPatient = patientRepository.findByEmail(email);
        if (existingPatient.filter(patient ->
            !patient.getIdCardNo().equals(updatedPatient.getIdCardNo())).isPresent()){
            throw new IdNumberModificationException("Nie można wprowadzic modyfikacji numeru dowodu");
        }
        //Dodaj walidację na sprawdzenie czy updatedPatient ma takis sam idNo
        if (updatedPatient.getFirstName() == null ||
        updatedPatient.getLastName() == null ||
        updatedPatient.getBirthday() == null ||
        updatedPatient.getPassword() == null ||
        updatedPatient.getEmail() == null ||
        updatedPatient.getPhoneNumber() == null) {
            throw new NotAllowedNullExpception("Nie można ustawić wartości jako null");
        }

        Optional<Patient> patientWithSameEmail = patientRepository.findByEmail(updatedPatient.getEmail());
        if (patientWithSameEmail.isPresent() && !patientWithSameEmail.get().getEmail().equals(email)) {
            throw new EmailExistingException("Istnieje już pacjent z takim adresem email");
        }
        return patientRepository.updatePatientByEmail(email, updatedPatient);
    }

    public Patient updatePassword(Patient patient, String updatePassoword) {
        patientRepository.updatePassword(patient, updatePassoword);
        return patient;
    }

    public boolean deleteByEmail(String email) {
        return patientRepository.deleteByEmail(email);
    }
}

