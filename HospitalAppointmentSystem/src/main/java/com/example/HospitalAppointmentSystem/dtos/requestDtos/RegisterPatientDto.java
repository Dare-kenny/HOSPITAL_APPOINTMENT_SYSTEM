package com.example.HospitalAppointmentSystem.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDto { // dto wrapper registering a patient
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

}
