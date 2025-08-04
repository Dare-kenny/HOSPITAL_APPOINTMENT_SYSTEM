package com.example.HospitalAppointmentSystem.dtos.requestDtos;


import com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule.DoctorScheduleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDoctorDto { // dto wrapper for Doctor registration.
    private String Name;
    private String Email;
    private String Password;
    private String Specialization;
    private List<DoctorScheduleResponse> Schedule;
}
