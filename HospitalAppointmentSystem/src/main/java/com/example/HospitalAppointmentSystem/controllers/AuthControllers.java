package com.example.HospitalAppointmentSystem.controllers;

import com.example.HospitalAppointmentSystem.dtos.requestDtos.LoginDto;
import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterPatientDto;
import com.example.HospitalAppointmentSystem.services.AuthenticationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

    @Autowired
    private AuthenticationServices authServices;

    @PostMapping("/register") // anyone registering from this endpoint is a patient , doctors are registered by HODs upon employment
                              // and HODs are manually put into the database for security reasons.
    public ResponseEntity<?> registration(@RequestBody RegisterPatientDto registerDto){
        return authServices.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

        return authServices.userLogin(loginDto);
    }
}
