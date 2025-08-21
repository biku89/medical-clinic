package com.biku89.medical_clinic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients") //każdy endpoint zaczyna się od tej ścieżki
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get all patients")
    @ApiResponse(responseCode = "200", description = "Patient list",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping
    public PageDTO<PatientDTO> getPatients(Pageable pageable) {
        return patientService.getPatients(pageable);
    }

    @Operation(summary = "Get patient by email")
    @ApiResponse(responseCode = "200", description = "Patient found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable("email") String email) {
        return patientService.getPatientByEmail(email);
    }

    @Operation(summary = "Add new patient")
    @ApiResponse(responseCode = "200", description = "Patient successfully added",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "409", description = "Email already exist",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PostMapping
    public PatientDTO addPatients(@RequestBody PatientDTO patient) {
        return patientService.addPatient(patient);
    }

    @Operation(summary = "Update existing patient")
    @ApiResponse(responseCode = "200", description = "Patient successfully updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "409", description = "Email already exist",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PutMapping("/{email}")
    //Responseentity pozwala kontrolować status http (200,404 itd.)
    public ResponseEntity<PatientDTO> updatePatients(@PathVariable String email, @RequestBody PatientUpdateCommand updatePatient) {
        return ResponseEntity.ok(patientService.updatePatient(email, updatePatient));
    }

    @Operation(summary = "Delete patient by email")
    @ApiResponse(responseCode = "200", description = "Patient successfully deleted",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deleteByEmail(email);
    }

    @Operation(summary = "Change patient password")
    @ApiResponse(responseCode = "200", description = "Password successfully updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    @PatchMapping("/{email}")
    public PatientDTO patientUpdatePassword(@PathVariable String email, @RequestBody Patient updatePassword) {
        return patientService.updatePassword(email, updatePassword.getPassword());
    }

    @DeleteMapping
    public void deletePatients(@RequestBody PatientsDeleteCommand deletePatients) {
        patientService.deletePatients(deletePatients);
    }
}

