package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public List<InstitutionDTO> getAllInstitutions(){
        return institutionRepository.findAll().stream().map(institutionMapper::toDTO).toList();
    }

    public Optional<InstitutionDTO> getInstitutionsById(Long id){
        return institutionRepository.findById(id).map(institutionMapper::toDTO);
    }

    public InstitutionDTO addInstitution(InstitutionDTO institutionDTO){
        if (institutionRepository.findByName(institutionDTO.name()).isPresent()){
            throw new IstitutionExistingException("Institution with this name already exists");
        }
        Institution institution = institutionMapper.toEntity(institutionDTO);
        institutionRepository.save(institution);
        return institutionMapper.toDTO(institution);
    }

    public void deleteInstitution(String name) {
        institutionRepository.deleteById(name);
    }

}
