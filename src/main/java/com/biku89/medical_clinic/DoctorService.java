package com.biku89.medical_clinic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final InstitutionRepository institutionRepository;

    public List<DoctorDTO> getAllDoctors(){
        return doctorRepository.findAll().stream().map(doctorMapper::toDTO).toList();
    }

    public Optional<DoctorDTO> getDoctorByEmail(String email){
        return doctorRepository.findByEmail(email).map(doctorMapper::toDTO);
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO){
        if (doctorRepository.findByEmail(doctorDTO.email()).isPresent()){
            throw new EmailExistingException("Doctor with this email already exists");
        }
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctorRepository.save(doctor);
        return doctorMapper.toDTO(doctor);
    }

    public DoctorDTO updateDoctor(String email, DoctorUpdateCommand doctorUpdateCommand){
        Doctor doctorUpdate = doctorMapper.toEntity(doctorUpdateCommand);
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new EmailExistingException("Doctor already exists"));

        existingDoctor.setFirstName(doctorUpdate.getFirstName());
        existingDoctor.setLastName(doctorUpdate.getLastName());
        existingDoctor.setPassword(doctorUpdate.getPassword());
        existingDoctor.setEmail(doctorUpdate.getEmail());

        return doctorMapper.toDTO(doctorRepository.save(existingDoctor));

    }
    public void deleteDoctorByEmail(String email){
        Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(() -> new EmailExistingException("Doctor already exists"));
        doctorRepository.delete(doctor);
    }

}
