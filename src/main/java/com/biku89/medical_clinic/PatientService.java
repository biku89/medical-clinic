package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PageDTO<PatientDTO> getPatients(Pageable pageable) {
        return PageDTO.from(patientRepository.findAll(pageable), patientMapper::toDTO);
    }

    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findByEmail(email).map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not found"));
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

        PatientValidator.useValidatorMethods(existingPatient, updatedPatient, email, patientRepository);

        existingPatient.updatePatient(updatedPatientDTO);

        return patientMapper.toDTO(patientRepository.save(existingPatient));
    }

    public PatientDTO updatePassword(String email, String updatePassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found."));

        patient.setPassword(updatePassword);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    public void deleteByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        patientRepository.delete(patient);
    }

    public void deletePatients(PatientsDeleteCommand patientsDeleteCommand) {
        patientRepository.deleteAllById(patientsDeleteCommand.getIds());
    }
}

