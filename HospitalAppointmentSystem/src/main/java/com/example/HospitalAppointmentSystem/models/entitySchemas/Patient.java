package com.example.HospitalAppointmentSystem.models.entitySchemas;

import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterPatientDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "patient")
public class Patient extends UserEntity {

    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be exactly 11 digits")
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public static Patient fromDto(RegisterPatientDto registerDto){
        return Patient.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .phoneNumber(registerDto.getPhoneNumber())
                .build();
    }



}
