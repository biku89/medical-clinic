package com.example.patient_service.client;

import com.example.patient_service.fallback.MedicalClinicFallback;
import com.example.patient_service.model.DoctorDTO;
import com.example.patient_service.model.DoctorSpecialization;
import com.example.patient_service.model.FeignConfig;
import com.example.patient_service.model.VisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "medical-clinic",url = "${MEDICAL_CLINIC_URL:http://localhost:8080}", configuration = FeignConfig.class, fallbackFactory = MedicalClinicFallback.class)
public interface MedicalClinicClient {

    @GetMapping("/visits/patient/{patientId}")
    List<VisitDTO> getVisitsForPatient(@PathVariable Long patientId);

    @GetMapping("visits/available/doctor/{doctorId}")
    List<VisitDTO> getAvailableVisitsForDoctor(@PathVariable Long doctorId);

    @PostMapping("visits/{visitId}/book/{patientId}")
    VisitDTO bookVisit(@PathVariable Long visitId, @PathVariable Long patientId);

    @GetMapping("visits/available/{specialization}/{date}")
    List<VisitDTO> getAvailableVisitsBySpecializationAndDate(
            @PathVariable DoctorSpecialization specialization,
            @PathVariable String date);

    @GetMapping("visits/doctor/{doctorId}/all")
    List<VisitDTO> getAllVisitForDoctor(
            @PathVariable Long doctorId
    );

    @DeleteMapping("/visits/delete/{visitId}")
    void deleteVisit(@PathVariable Long visitId);

    @GetMapping("doctors/specialization/{specialization}")
    List<DoctorDTO> getDoctorBySpecialization(
            @PathVariable DoctorSpecialization specialization
    );

    @GetMapping("visits/specialization/{specialization}")
    List<VisitDTO> getVisitsBySpecializationAndSpecificTimePeriod(
            @PathVariable DoctorSpecialization specialization,
            @RequestParam String from,
            @RequestParam String to);

    @GetMapping("visits/available/all")
    List<VisitDTO> getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(
            @RequestParam String from,
            @RequestParam String to);

}


