package com.example.HospitalAppointmentSystem.repositories;

import com.example.HospitalAppointmentSystem.models.entitySchemas.HOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HODRepository extends JpaRepository<HOD,Long> {
    public Optional<HOD> findByEmail(String email);

}
