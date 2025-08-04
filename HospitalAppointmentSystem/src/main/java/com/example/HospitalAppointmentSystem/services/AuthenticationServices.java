package com.example.HospitalAppointmentSystem.services;

import com.example.HospitalAppointmentSystem.configurations.jwtConfig.JwtUtil;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import com.example.HospitalAppointmentSystem.models.enums.Role;
import com.example.HospitalAppointmentSystem.repositories.PatientRepository;
import com.example.HospitalAppointmentSystem.dtos.requestDtos.LoginDto;
import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterPatientDto;
import com.example.HospitalAppointmentSystem.dtos.responseDtos.SuccessfulPatientReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationServices { //Service for user login
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> userLogin(@RequestBody LoginDto loginDto){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(token); // use this token to access protected endpoints , enter it into Authorization header
    }

    public ResponseEntity<?> registerUser(@RequestBody RegisterPatientDto registerDto){

        Patient patient = Patient.fromDto(registerDto); // Dtos are then mapped to the Patient entites which are stored into the database
        patient.setRole(Role.PATIENT); // automatically sets their role to patient
        patient.setPassword(passwordEncoder.encode(patient.getPassword())); //encodes the password to be saved to avoid saving raw passwords
        patientRepository.save(patient); //saves entity

        if (registerDto.getPassword() == null || registerDto.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }

        System.out.println("Raw Password: " + registerDto.getPassword());

        SuccessfulPatientReg successfulPatientReg= SuccessfulPatientReg.fromDatabaseToDto(patient); // wrap in dto before responding

        return ResponseEntity.ok(successfulPatientReg+"\n"+successfulPatientReg.SuccesfulRegMessage()); //successful response
    }
}
