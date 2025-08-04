package com.example.HospitalAppointmentSystem.models.entitySchemas;

import com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule.DoctorScheduleResponse;
import com.example.HospitalAppointmentSystem.models.otherSchemas.AllSlots;

import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterDoctorDto;
import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "doctor")
public class Doctor extends UserEntity {

    private String Specialization;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AllSlots> slots = new ArrayList<>() ;

    public static Doctor fromDto(RegisterDoctorDto registerDoctorDto){
        Doctor doctor= Doctor.builder()
                             .name(registerDoctorDto.getName())
                             .email(registerDoctorDto.getEmail())
                             .password(registerDoctorDto.getPassword())
                             .Specialization(registerDoctorDto.getSpecialization())
                             .build();

        List<AllSlots> slotEntityDto = new ArrayList<>();

        if(registerDoctorDto.getSchedule() != null){
            for (DoctorScheduleResponse slotDto : registerDoctorDto.getSchedule()){
                AllSlots slot = AllSlots.builder()
                        .day(DayOfWeek.valueOf(slotDto.getSlot().getDay().toUpperCase()))
                        .timeSlots(slotDto.getSlot().getTimeSlots())
                        .doctor(doctor)
                        .build();
                slotEntityDto.add(slot);
            }
        }

        doctor.setSlots(slotEntityDto);
        return doctor;
    }

}
