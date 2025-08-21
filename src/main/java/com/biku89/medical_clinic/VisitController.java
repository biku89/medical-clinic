package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;

    @PostMapping("/doctor/{doctorId}")
    public VisitDTO createVisit(@PathVariable Long doctorId, @RequestBody VisitCreateRequest request){
        return visitService.createVisit(doctorId, request.getDateTime());
    }

    @PostMapping("/{visitId}/book/{patientId}")
    public VisitDTO bookVisit(@PathVariable Long visitId, @PathVariable Long patientId){
        return visitService.bookVisit(visitId,patientId);
    }

    @GetMapping("/patient/{patientId}")
    public List<VisitDTO> getVisitsForPatient(@PathVariable Long patientId){
        return visitService.getVisitsForPatient(patientId);
    }
}
