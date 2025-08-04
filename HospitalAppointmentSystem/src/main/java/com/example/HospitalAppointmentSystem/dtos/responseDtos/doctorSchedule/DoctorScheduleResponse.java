package com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleResponse { // dto wrapper structure for showing the doctor Schedule

    private PatientInfo patient;
    private SlotDto slot;

}
