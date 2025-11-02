package com.example.patient_service.service;

import com.example.patient_service.client.MedicalClinicClient;
import com.example.patient_service.exception.NotFoundException;
import com.example.patient_service.model.DoctorDTO;
import com.example.patient_service.model.DoctorSpecialization;
import com.example.patient_service.model.VisitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final MedicalClinicClient medicalClinicClient;

    public List<VisitDTO> getVisitForPatient(Long id){
        List<VisitDTO> visit = medicalClinicClient.getVisitsForPatient(id);
        if (visit.isEmpty()){
            throw new NotFoundException("No visits found for patient with id " + id);
        }
        return visit;
    }

    public List<VisitDTO> getAvailableVisitsForDoctor(Long doctorId){
        return medicalClinicClient.getAvailableVisitsForDoctor(doctorId);
    }

    public VisitDTO bookVisit(Long visitId, Long patientId){
        return medicalClinicClient.bookVisit(visitId, patientId);
    }

    public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(DoctorSpecialization specialization, LocalDate date){
        String formatDate = date.toString();
        return medicalClinicClient.getAvailableVisitsBySpecializationAndDate(specialization,formatDate);
    }

    public List<VisitDTO> getAllVisitsForDoctor(Long doctorId){
        return medicalClinicClient.getAllVisitForDoctor(doctorId);
    }

    public void deleteVisit(Long visitId){
        medicalClinicClient.deleteVisit(visitId);
    }

    public List<DoctorDTO> getDoctorsBySpecialization(DoctorSpecialization specialization){
        return medicalClinicClient.getDoctorBySpecialization(specialization);
    }

    public List<VisitDTO> getVisitsBySpecializationAndSpecificTimePeriod(DoctorSpecialization specialization, LocalDateTime from, LocalDateTime to){
        return medicalClinicClient.getVisitsBySpecializationAndSpecificTimePeriod(specialization, from.toString(), to.toString());
    }

    public List<VisitDTO> getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(LocalDateTime from, LocalDateTime to){
        return medicalClinicClient.getAllVisitsWithAndWithoutSpecializationForSpecificTimePeriod(from.toString(), to.toString());
    }
}
