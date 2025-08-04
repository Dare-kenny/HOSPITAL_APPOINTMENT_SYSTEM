package com.example.HospitalAppointmentSystem.dtos.responseDtos.availableSlots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSlotResponse { // dto wrapper for showing the available slots to a patient

    private Long slotId;
    private String day;
    private String timeSlots;
    private DoctorInfo doctor;
}
