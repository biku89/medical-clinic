package com.biku89.medical_clinic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
    void deleteByEmail(String email); // alternatywnie mogę użyć tego do usunięcia
}
