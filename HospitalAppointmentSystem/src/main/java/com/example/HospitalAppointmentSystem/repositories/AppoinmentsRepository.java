package com.example.HospitalAppointmentSystem.repositories;

import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppoinmentsRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByDoctorId(Long doctorId);
}
