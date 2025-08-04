package com.example.HospitalAppointmentSystem.dtos.requestDtos;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import com.example.HospitalAppointmentSystem.models.entitySchemas.HOD;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto { //dto wrapper for UserLogin

    private String email;
    private String password;

    public LoginDto(Patient patient){
        this.email = patient.getEmail();
        this.password = patient.getPassword();
    }

    public LoginDto(Doctor doctor){
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
    }

    public LoginDto(HOD hod){
        this.email = hod.getEmail();
        this.password = hod.getPassword();
    }
}
