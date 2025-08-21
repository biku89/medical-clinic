package com.biku89.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    public VisitDTO createVisit(Long doctorId, CreateVisitCommand createVisitCommand) { //STWÓRZ DTO
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        LocalDateTime startVisit = createVisitCommand.getStartDateTime();
        LocalDateTime endVisit = createVisitCommand.getEndDateTime();

        VisitValidator.visitNotInPast(startVisit);
        VisitValidator.visitStartBeforeEnd(startVisit,endVisit);
        VisitValidator.visitFullQuarterHour(startVisit);

        List<Visit> conflictVisits = visitRepository.findByDoctorIdAndEndDateTimeAfterAndStartDateTimeBefore(doctorId, startVisit, endVisit);
        VisitValidator.visitConflicts(conflictVisits);

        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setStartDateTime(startVisit);
        visit.setEndDateTime(endVisit);

        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);

    }

    public VisitDTO bookVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        VisitValidator.visitNotInPast(visit.getStartDateTime());
        VisitValidator.visitAlreadyBooked(visit);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        visit.setPatient(patient);
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }

    public List<VisitDTO> getVisitsForPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        return visitRepository.findByPatient(patient).stream()
                .map(visitMapper::toDTO)
                .toList();
    }
}
