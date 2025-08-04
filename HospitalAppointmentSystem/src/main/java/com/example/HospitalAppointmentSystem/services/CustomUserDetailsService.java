package com.example.HospitalAppointmentSystem.services;

import com.example.HospitalAppointmentSystem.models.CustomUserDetails;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import com.example.HospitalAppointmentSystem.models.entitySchemas.HOD;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import com.example.HospitalAppointmentSystem.repositories.DoctorRepository;
import com.example.HospitalAppointmentSystem.repositories.HODRepository;
import com.example.HospitalAppointmentSystem.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HODRepository hodRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Patient> patientUser = patientRepository.findByEmail(email);
        Optional<Doctor> doctorUser = doctorRepository.findByEmail(email);
        Optional<HOD> hodUser = hodRepository.findByEmail(email);

        if(patientUser.isPresent()){ // if user is a patient
            Patient patient = patientUser.get();
            System.out.println(">>> PATIENT ROLE: " + patient.getRole()); // logging
            System.out.println(">>> AUTHORITY: ROLE_" + patient.getRole());
             return CustomUserDetails.builder()//
                     .id(patient.getId())
                     .username(patient.getEmail())
                     .password(patient.getPassword())
                     .authorities(List.of(() -> "ROLE_" + patient.getRole())) // get role so that he authorised endpoints can be accessed
                     .build();

        }
        if(doctorUser.isPresent()){
            Doctor doctor = doctorUser.get();
            return CustomUserDetails.builder()
                    .id(doctor.getId())
                    .username(doctor.getEmail())
                    .password(doctor.getPassword())
                    .authorities(List.of(() -> "ROLE_" + doctor.getRole()))
                    .build();
        }

        if(hodUser.isPresent()){
            HOD hod = hodUser.get();
            return CustomUserDetails.builder()
                    .id(hod.getId())
                    .username(hod.getEmail())
                    .password(hod.getPassword())
                    .authorities(List.of(() -> "ROLE_" + hod.getRole()))
                    .build();
        }



        throw new UsernameNotFoundException("user with email "+email+" not found");

    }

}



