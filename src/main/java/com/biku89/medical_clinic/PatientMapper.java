package com.biku89.medical_clinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDTO(Patient patient);
    Patient toEntity(PatientDTO patientDTO);
    Patient toEntity(PatientUpdateCommand patientUpdateCommand);

}
