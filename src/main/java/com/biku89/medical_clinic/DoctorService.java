package com.biku89.medical_clinic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final InstitutionRepository institutionRepository;

    public PageDTO<DoctorDTO> getAllDoctors(Pageable pageable){
        return PageDTO.from(doctorRepository.findAll(pageable), doctorMapper::toDTO);
    }

    public DoctorDTO getDoctorByEmail(String email){
        return doctorRepository.findByEmail(email)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new DoctorNotFoundException ("Doctor not found"));
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
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        existingDoctor.updateDoctor(doctorUpdateCommand);
        return doctorMapper.toDTO(doctorRepository.save(existingDoctor));
    }

    public void deleteDoctorByEmail(String email){
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        doctorRepository.delete(doctor);
    }

    public Institution assignClinic(String email, String name){
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found"));
        doctor.getInstitutions().add(institution);
        doctorRepository.save(doctor);
        return institution;
    }
}
