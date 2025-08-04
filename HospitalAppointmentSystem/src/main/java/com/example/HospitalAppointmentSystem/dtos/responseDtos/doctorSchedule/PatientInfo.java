package com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientInfo { // a sub dto for the doctor schedule dto
    private String name;
}
