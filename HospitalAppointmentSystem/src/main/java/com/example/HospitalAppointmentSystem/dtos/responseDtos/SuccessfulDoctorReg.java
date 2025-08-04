package com.example.HospitalAppointmentSystem.dtos.responseDtos;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessfulDoctorReg { // dto for successful doctor registration

    private String name;
    private String email;
    private String specialization;

    public static SuccessfulDoctorReg fromDatabaseToDto(Doctor doctor){
        return SuccessfulDoctorReg.builder()
                .name(doctor.getName())
                .email(doctor.getEmail())
                .specialization(doctor.getSpecialization())
                .build();
    }
    public String SuccesfulRegMessage(){
        return " Successfully registered "+getName();
    }
}
