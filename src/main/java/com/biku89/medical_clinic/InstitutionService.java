package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public PageDTO<InstitutionDTO> getAllInstitutions(Pageable pageable){
        return PageDTO.from(institutionRepository.findAll(pageable), institutionMapper::toDTO);
    }

    public InstitutionDTO getInstitutionsById(Long id){
        return institutionRepository.findById(id).map(institutionMapper::toDTO)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found"));
    }

    public InstitutionDTO addInstitution(InstitutionDTO institutionDTO){
        if (institutionRepository.findByName(institutionDTO.name()).isPresent()){
            throw new InstitutionExistingException("Institution with this name already exists");
        }
        Institution institution = institutionMapper.toEntity(institutionDTO);
        institutionRepository.save(institution);
        return institutionMapper.toDTO(institution);
    }

    public void deleteInstitution(String name) {
        Institution institution = institutionRepository.findByName(name)
                        .orElseThrow(() -> new InstitutionNotFoundException("Institution not found"));
        institutionRepository.delete(institution);
    }

}
