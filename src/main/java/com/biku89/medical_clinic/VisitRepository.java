package com.biku89.medical_clinic;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatient(Patient patient);
    boolean existsVisitByDoctorAndDateTime(Doctor doctor, LocalDateTime dateTime);
}
