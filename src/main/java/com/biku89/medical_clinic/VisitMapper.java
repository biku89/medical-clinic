package com.biku89.medical_clinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DoctorMapper.class, PatientMapper.class})
public interface VisitMapper {
    VisitDTO toDTO(Visit visit);
}
