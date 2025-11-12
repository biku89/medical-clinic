package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;

    @PostMapping("/doctor/{doctorId}")
    public VisitDTO createVisit(@PathVariable Long doctorId, @RequestBody CreateVisitCommand request){
        return visitService.createVisit(doctorId, request);
    }

    @PostMapping("/{visitId}/book/{patientId}")
    public VisitDTO bookVisit(@PathVariable Long visitId, @PathVariable Long patientId){
        return visitService.bookVisit(visitId,patientId);
    }

    @GetMapping("/patient/{patientId}")
    public List<VisitDTO> getVisitsForPatient(@PathVariable Long patientId){
        return visitService.getVisitsForPatient(patientId);
    }

    @GetMapping("/available/doctor/{doctorId}")
    public List<VisitDTO> getVisitCreatedByDoctor(@PathVariable Long doctorId){
        return visitService.getAvailableVisitsForDoctor(doctorId);
    }

    @GetMapping("/available/{specialization}/{date}")
    public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(
            @PathVariable DoctorSpecialization specialization,
            @PathVariable LocalDate date){
        return visitService.getAvailableVisitsBySpecializationAndDate(specialization,date);
    }

    @GetMapping("/doctor/{doctorId}/all")
    public List<VisitDTO> getAllVisitForDoctor(
            @PathVariable Long doctorId){
        return visitService.getAllVisitForDoctor(doctorId);
    }

    @DeleteMapping("/delete/{visitId}")
    public void deleteVisit(@PathVariable Long visitId){
        visitService.deleteVisit(visitId);
    }

    @GetMapping("/specialization/{specialization}")
    public List<VisitDTO> getVisitsBySpecializationAndSpecificTimePeriod(
            @PathVariable DoctorSpecialization specialization,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to){
        return visitService.getVisitsBySpecializationAndSpecificTimePeriod(specialization,from,to);
    }

    @GetMapping("/available/all")
    public List<VisitDTO> getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ){
        return visitService.getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(from,to);
    }
}
