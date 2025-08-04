package com.example.HospitalAppointmentSystem.services.entityServices;

import com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule.DoctorScheduleResponse;
import com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule.PatientInfo;
import com.example.HospitalAppointmentSystem.dtos.responseDtos.doctorSchedule.SlotDto;
import com.example.HospitalAppointmentSystem.models.CustomUserDetails;
import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import com.example.HospitalAppointmentSystem.repositories.AppoinmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServices {

    @Autowired
    private AppoinmentsRepository appoinmentsRepository;

    public ResponseEntity<?> viewSchedule(){ // doctor service to view schedule
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get logged-in user details
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // convert it to my customUserDetail

        Long DoctorId = userDetails.getId(); // get id of logged-in user

        List<Appointment> appointments = appoinmentsRepository.findByDoctorId(DoctorId);

        //view the appointment in this structure:
        /*
        * {
        *
        *  "patient":{
        *     "name":
        *            },
        * "slot":{
        *     "day":,
        *     "timeslot":
        *        }
        *
        * }
        *
        * */

        List<DoctorScheduleResponse> scheduleList = appointments.stream().map(appointment -> {
            PatientInfo patientInfo = PatientInfo.builder()
                    .name(appointment.getPatient().getName())
                    .build();

            SlotDto slotDto = SlotDto.builder()
                    .day(appointment.getSlot().getDay().toString())
                    .timeSlots(appointment.getSlot().getTimeSlots())
                    .build();

            return DoctorScheduleResponse.builder()
                    .patient(patientInfo)
                    .slot(slotDto)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(scheduleList);
    }
}
