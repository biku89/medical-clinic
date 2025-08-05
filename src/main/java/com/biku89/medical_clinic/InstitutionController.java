package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping
    public List<InstitutionDTO> getAllInstitutions(){
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDTO> getInstitutionById(@PathVariable("id") Long id){
        return institutionService.getInstitutionsById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InstitutionDTO addInstitution(@RequestBody InstitutionDTO institution){
        return institutionService.addInstitution(institution);
    }

    @DeleteMapping("/{name}")
    public void deleteInstitutionByName(@PathVariable("name") String name){
        institutionService.deleteInstitution(name);
    }
}
