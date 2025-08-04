package com.example.HospitalAppointmentSystem.controllers;

import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterDoctorDto;
import com.example.HospitalAppointmentSystem.services.entityServices.HODServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hod")
public class HODControllers {

    @Autowired
    private HODServices hodServices;

    @PostMapping("/register-Doctor")
    public ResponseEntity<?> RegisterDoctors(@RequestBody RegisterDoctorDto registerDoctorDto){
        return hodServices.registerDoctor(registerDoctorDto);
    }
}
