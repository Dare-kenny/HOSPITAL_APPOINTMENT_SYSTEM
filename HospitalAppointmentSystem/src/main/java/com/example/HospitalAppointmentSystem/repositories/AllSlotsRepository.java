package com.example.HospitalAppointmentSystem.repositories;

import com.example.HospitalAppointmentSystem.models.otherSchemas.AllSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllSlotsRepository extends JpaRepository<AllSlots,Long> {
    List<AllSlots> findByIsBookedFalse();
}
