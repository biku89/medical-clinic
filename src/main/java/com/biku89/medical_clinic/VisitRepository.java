package com.biku89.medical_clinic;

import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatient(Patient patient);
    List<Visit> findByDoctor(Doctor doctor);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND v.endDateTime > :start " +
            "AND v.startDateTime < :end")
    List<Visit> findConflictingVisits(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<Visit> findByDoctorIdAndEndDateTimeAfterAndStartDateTimeBefore(
            Long doctorId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Visit> findByDoctorIdAndPatientIsNullAndStartDateTimeAfter(
            Long doctorId, LocalDateTime from);

    List<Visit> findByDoctor_DoctorSpecializationAndPatientIsNullAndStartDateTimeBetween(
            DoctorSpecialization specialization,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Visit> findByDoctorId(Long doctorId);

    List<Visit> findByDoctor_DoctorSpecializationAndStartDateTimeBetween(
            DoctorSpecialization specialization,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Visit> findByPatientIsNullAndStartDateTimeBetween(
            LocalDateTime from,
            LocalDateTime to
    );

}
