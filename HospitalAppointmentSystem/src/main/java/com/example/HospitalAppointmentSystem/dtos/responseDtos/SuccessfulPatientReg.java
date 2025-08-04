package com.example.HospitalAppointmentSystem.dtos.responseDtos;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
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
public class SuccessfulPatientReg { //dto wrapper for successful patient registration

    private String Name;
    private String Email;
    private String PhoneNumber;
    private List<Appointment> Appointments;

    public static SuccessfulPatientReg fromDatabaseToDto(Patient patient){
        return SuccessfulPatientReg.builder()
                .Name(patient.getName())
                .Email(patient.getEmail())
                .PhoneNumber(patient.getPhoneNumber())
                .Appointments(patient.getAppointments())
                .build();
    }

    public String SuccesfulRegMessage(){
        return " Successfully registered "+getName();
    }
}
