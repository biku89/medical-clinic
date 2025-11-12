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
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final InstitutionMapper institutionMapper;

    @Operation(summary = "Get all doctors")
    @ApiResponse(responseCode = "200", description = "Doctor list",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping
    public PageDTO<DoctorDTO> getAllDoctors(Pageable pageable){
        return doctorService.getAllDoctors(pageable);
    }

    @Operation(summary = "Get doctor by email")
    @ApiResponse(responseCode = "200", description = "Doctor found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Doctor not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping("/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable("email")String email){
        return doctorService.getDoctorByEmail(email);
    }

    @Operation(summary = "Add new doctor")
    @ApiResponse(responseCode = "200", description = "Doctor successfully added",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "409", description = "Email already exist",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PostMapping
    public DoctorDTO addDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.addDoctor(doctor);
    }

    @Operation(summary = "Update existing doctor")
    @ApiResponse(responseCode = "200", description = "Doctor successfully updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Doctor not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "409", description = "Email already exist",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PutMapping("/{email}")
    public DoctorDTO updateDoctor(@PathVariable("email") String email, @RequestBody DoctorUpdateCommand doctorUpdate){
        return doctorService.updateDoctor(email, doctorUpdate);
    }

    @Operation(summary = "Delete doctor by email")
    @ApiResponse(responseCode = "200", description = "Doctor successfully deleted",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Doctor not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @DeleteMapping("/{email}")
    public void deleteDoctorByEmail(@PathVariable("email")String email){
        doctorService.deleteDoctorByEmail(email);
    }

    @PatchMapping("/{email}")
    public InstitutionDTO assignInstitutionToDoctor(@PathVariable String email, @RequestBody InstitutionAssignRequest InstitutionAssign){
        return  institutionMapper.toDTO(doctorService.assignClinic(email, InstitutionAssign.getName()));
    }

    @GetMapping("/specialization/{specialization}")
    public List<DoctorDTO> getDoctorsBySpecialization(@PathVariable DoctorSpecialization specialization){
        return doctorService.getDoctorsBySpecialization(specialization);
    }
}
