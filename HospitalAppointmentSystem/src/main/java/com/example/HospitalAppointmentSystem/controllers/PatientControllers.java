package com.example.HospitalAppointmentSystem.controllers;

import com.example.HospitalAppointmentSystem.dtos.requestDtos.BookSlotRequest;
import com.example.HospitalAppointmentSystem.services.entityServices.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patient")
public class PatientControllers {

    @Autowired
    private PatientServices patientServices;

    @GetMapping("/view-available-Slots")
    public ResponseEntity<?> viewAppointments(){
        return patientServices.getAllAvailableSlots();
    }

    @PostMapping("/book-Appointment")
    public ResponseEntity<?> bookAppointment(@RequestBody BookSlotRequest bookSlotRequest){
        return patientServices.bookSlot(bookSlotRequest.getSlotId());
    }

}
