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

    public VisitDTO createVisit(Long doctorId, LocalDateTime dateTime){ //STWÓRZ DTO
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IncorrectDataException("The visit cannot be in the past");
        }

        if (visitRepository.existsVisitByDoctorAndDateTime(doctor, dateTime)){
            throw new VisitExistingException("This doctor already has a visit at the given time");
        }

        int minute = dateTime.getMinute();
        if (minute != 0 &&minute != 15 && minute != 30 && minute != 45){
            throw new IllegalArgumentException("The deadline must start on the full quarter of an hour");
        }

        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setDateTime(dateTime);

        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);

    }

    public VisitDTO bookVisit(Long visitId, Long patientId){
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (visit.getDateTime().isBefore(LocalDateTime.now())){
            throw new IncorrectDataException("The visit cannot be in the past");
        }


        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        if (visit.getPatient() != null){
            throw new IllegalArgumentException("The visit is already booked");//zrób własny warunek
        }

        visit.setPatient(patient);
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }

    public List<VisitDTO> getVisitsForPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        return visitRepository.findByPatient(patient)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }
}
