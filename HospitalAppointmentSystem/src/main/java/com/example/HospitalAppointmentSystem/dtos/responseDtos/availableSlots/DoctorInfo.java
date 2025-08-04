package com.example.HospitalAppointmentSystem.dtos.responseDtos.availableSlots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfo { // a sub dto for the available slot response

    private String name;
    private String specialization;
}
