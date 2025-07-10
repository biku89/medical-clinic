package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Patient> getPateintByEmail(@PathVariable("email") String email) {
        return patientService.getPatientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patient addPatients(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @PutMapping("/{email}")
    //Responseentity pozwala kontrolować status http (200,404 itd.)
    public ResponseEntity<Patient> updatePatients(@PathVariable String email, @RequestBody Patient updatePatient) {
        return patientService.updatePatient(email, updatePatient)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email) {
        patientService.deleteByEmail(email);
    }

}

