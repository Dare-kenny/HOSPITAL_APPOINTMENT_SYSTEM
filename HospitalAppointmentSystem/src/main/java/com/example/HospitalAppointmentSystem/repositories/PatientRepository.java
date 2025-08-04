package com.example.HospitalAppointmentSystem.repositories;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    public Optional<Patient> findByEmail(String email);
}
