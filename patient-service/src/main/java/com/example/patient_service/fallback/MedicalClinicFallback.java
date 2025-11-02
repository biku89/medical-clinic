package com.example.patient_service.fallback;

import com.example.patient_service.client.MedicalClinicClient;
import com.example.patient_service.model.DoctorDTO;
import com.example.patient_service.model.DoctorSpecialization;
import com.example.patient_service.model.VisitDTO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MedicalClinicFallback implements FallbackFactory<MedicalClinicClient> {
    @Override
    public MedicalClinicClient create(Throwable cause) {
        return new MedicalClinicClient() {
            @Override
            public List<VisitDTO> getVisitsForPatient(Long patientId) {
                return List.of();
            }

            @Override
            public List<VisitDTO> getAvailableVisitsForDoctor(Long doctorId) {
                return List.of();
            }

            @Override
            public VisitDTO bookVisit(Long visitId, Long patientId) {
                return null;
            }

            @Override
            public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(DoctorSpecialization specialization, String date) {
                return List.of();
            }

            @Override
            public List<VisitDTO> getAllVisitForDoctor(Long doctorId) {
                return List.of();
            }

            @Override
            public void deleteVisit(Long visitId) {

            }

            @Override
            public List<DoctorDTO> getDoctorBySpecialization(DoctorSpecialization specialization) {
                return List.of();
            }

            @Override
            public List<VisitDTO> getVisitsBySpecializationAndSpecificTimePeriod(DoctorSpecialization specialization, String from, String to) {
                return List.of();
            }

            @Override
            public List<VisitDTO> getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(String from, String to) {
                return List.of();
            }
        };
    }
}
