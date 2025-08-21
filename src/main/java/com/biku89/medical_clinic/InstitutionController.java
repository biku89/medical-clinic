package com.biku89.medical_clinic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;

    @Operation(summary = "Get all institutions")
    @ApiResponse(responseCode = "200", description = "Institution list",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping
    public PageDTO<InstitutionDTO> getAllInstitutions(Pageable pageable){
        return institutionService.getAllInstitutions(pageable);
    }

    @Operation(summary = "Get institution by id")
    @ApiResponse(responseCode = "200", description = "Institution found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Institution not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping("/{id}")
    public InstitutionDTO getInstitutionById(@PathVariable("id") Long id){
        return institutionService.getInstitutionsById(id);
    }

    @Operation(summary = "Add new institution")
    @ApiResponse(responseCode = "200", description = "Institution successfully added",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "409", description = "Name already exist",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PostMapping
    public InstitutionDTO addInstitution(@RequestBody InstitutionDTO institution){
        return institutionService.addInstitution(institution);
    }

    @Operation(summary = "Delete institution by name")
    @ApiResponse(responseCode = "200", description = "institution successfully deleted",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "institution not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @DeleteMapping("/{name}")
    public void deleteInstitutionByName(@PathVariable("name") String name){
        institutionService.deleteInstitution(name);
    }
}
