package com.example.HospitalAppointmentSystem.repositories;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor , Long> {

    public Optional<Doctor> findByEmail(String email);
}
