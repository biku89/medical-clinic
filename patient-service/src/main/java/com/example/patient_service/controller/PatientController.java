package com.example.patient_service.controller;

import com.example.patient_service.model.DoctorDTO;
import com.example.patient_service.model.DoctorSpecialization;
import com.example.patient_service.model.VisitDTO;
import com.example.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/patient/{patientId}")
    public List<VisitDTO> getVisits(@PathVariable Long patientId) {
        return patientService.getVisitForPatient(patientId);
    }

    @GetMapping("/available/doctor/{doctorId}")
    public List<VisitDTO> getAvailableVisitsForDoctor(@PathVariable Long doctorId) {
        return patientService.getAvailableVisitsForDoctor(doctorId);
    }

    @PostMapping("/{visitId}/book/{patientId}")
    public VisitDTO bookVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        return patientService.bookVisit(visitId, patientId);
    }

    @GetMapping("/available/{specialization}/{date}")
    public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(
            @PathVariable DoctorSpecialization specialization,
            @PathVariable LocalDate date) {
        return patientService.getAvailableVisitsBySpecializationAndDate(specialization, date);
    }

    @GetMapping("/doctor/all/{doctorId}")
    public List<VisitDTO> getAllVisitForDoctor(
            @PathVariable Long doctorId
    ) {
        return patientService.getAllVisitsForDoctor(doctorId);
    }

    @DeleteMapping("/delete/{visitId}")
    public void deleteVisitByDoctor(@PathVariable Long visitId) {
        patientService.deleteVisit(visitId);
    }

    @GetMapping("/doctor/specialization/{specialization}")
    public List<DoctorDTO> getDoctorsBySpecialization(
            @PathVariable DoctorSpecialization specialization) {
        return patientService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("specialization/{specialization}")
    public List<VisitDTO> getVisitsBySpecializationAndSpecificTimePeriod(
            @PathVariable DoctorSpecialization specialization,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return patientService.getVisitsBySpecializationAndSpecificTimePeriod(specialization, from, to);
    }

    @GetMapping("/available/all")
    public List<VisitDTO> getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return patientService.getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(from, to);

    }
}
