package com.biku89.medical_clinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    Institution toEntity(InstitutionDTO institutionDTO);
    InstitutionDTO toDTO(Institution institution);
}
