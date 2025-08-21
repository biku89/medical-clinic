package com.biku89.medical_clinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toEntity(DoctorDTO doctorDTO);
    DoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(DoctorUpdateCommand doctorUpdateCommand);
}
