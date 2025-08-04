package com.example.HospitalAppointmentSystem.services.entityServices;

import com.example.HospitalAppointmentSystem.dtos.responseDtos.availableSlots.AvailableSlotResponse;
import com.example.HospitalAppointmentSystem.dtos.responseDtos.availableSlots.DoctorInfo;
import com.example.HospitalAppointmentSystem.models.CustomUserDetails;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import com.example.HospitalAppointmentSystem.models.otherSchemas.AllSlots;
import com.example.HospitalAppointmentSystem.models.otherSchemas.Appointment;
import com.example.HospitalAppointmentSystem.repositories.AllSlotsRepository;
import com.example.HospitalAppointmentSystem.repositories.AppoinmentsRepository;
import com.example.HospitalAppointmentSystem.repositories.PatientRepository;
import com.example.HospitalAppointmentSystem.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServices {

    @Autowired
    private AllSlotsRepository slotsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppoinmentsRepository appoinmentsRepository;

    @Autowired
    private EmailServices emailServices;


    public ResponseEntity<List<AvailableSlotResponse>> getAllAvailableSlots(){ //view available slots
        List<AllSlots> slots = slotsRepository.findByIsBookedFalse(); // find slot that isBooked is marked "false"

        //view available slot in the structure :
        /*
        * {
        * "slotId":,
        * "day":,
        * "timeslot":
        * "Doctor":{
        *        "name":,
        *        "specialization":
        *          }
        * }
        *
        */
        return ResponseEntity.ok(slots.stream().map(slot -> new AvailableSlotResponse(
                slot.getId(),
                slot.getDay().toString(),
                slot.getTimeSlots(),
                new DoctorInfo(
                        slot.getDoctor().getName(),
                        slot.getDoctor().getSpecialization()
                )
        )).collect(Collectors.toList()));

    }

    public ResponseEntity<?> bookSlot(Long slotId ){ // book slot by selecting its id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();// Get the name of the logged-in User

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long patientId = userDetails.getId(); // Get the id of the logged-in User


        AllSlots slot = slotsRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked())throw new RuntimeException("Slot is already booked");

        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new RuntimeException("Patien not found")); //search for the logged-in user id in the database

        slot.setBooked(true); // set isBooked to true
        slotsRepository.save(slot); // update the AllSlots schema

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .slot(slot)
                .doctor(slot.getDoctor())
                .build();



        appoinmentsRepository.save(appointment); //save to the appointments schema

        emailServices.sendAppointmentConfirmation(name, patient.getEmail(), slot); //send email confirmation

        return ResponseEntity.ok("Appointment Booked , Email sent!");
    }
}
