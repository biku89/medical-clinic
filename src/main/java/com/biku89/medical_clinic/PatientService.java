package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream().map(patientMapper::toDTO).toList();
    }

    public Optional<PatientDTO> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email).map(patientMapper::toDTO);
    }

    public PatientDTO addPatient(PatientDTO patientDTO) {
        if (patientRepository.findByEmail(patientDTO.email()).isPresent()) {
            throw new EmailExistingException("Patient with this email already exists");
        }
        Patient patient = patientMapper.toEntity(patientDTO);
        patientRepository.save(patient);
        return patientMapper.toDTO(patient);
    }

    public PatientDTO updatePatient(String email, PatientUpdateCommand updatedPatientDTO) {
        Patient updatedPatient = patientMapper.toEntity(updatedPatientDTO);
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        PatientValidator.modifyingIdCardNoNotAllowed(existingPatient, updatedPatient);
        PatientValidator.notAllowedChangeToNull(updatedPatient);

        Optional<Patient> patientWithSameEmail = patientRepository.findByEmail(updatedPatient.getEmail());
        PatientValidator.patientWithEmailIsAlreadyExist(patientWithSameEmail, updatedPatient, email);

        existingPatient.setFirstName(updatedPatientDTO.getFirstName());
        existingPatient.setLastName(updatedPatientDTO.getLastName());
        existingPatient.setEmail(updatedPatientDTO.getEmail());
        existingPatient.setPhoneNumber(updatedPatientDTO.getPhoneNumber());
        existingPatient.setBirthday(updatedPatientDTO.getBirthday());

        return patientMapper.toDTO(patientRepository.save(existingPatient));
    }

    public PatientDTO updatePassword(String email, String updatePassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found."));

        patient.setPassword(updatePassword);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    public boolean deleteByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        patientRepository.delete(patient);
        return true;
    }
}

