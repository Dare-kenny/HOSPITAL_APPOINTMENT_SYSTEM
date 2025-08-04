package com.example.HospitalAppointmentSystem.services.entityServices;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import com.example.HospitalAppointmentSystem.models.enums.Role;
import com.example.HospitalAppointmentSystem.repositories.DoctorRepository;
import com.example.HospitalAppointmentSystem.dtos.requestDtos.RegisterDoctorDto;
import com.example.HospitalAppointmentSystem.dtos.responseDtos.SuccessfulDoctorReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class HODServices {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerDoctor(@RequestBody RegisterDoctorDto registerDoctorDto){ //service to register a doctor

        Doctor doctor = Doctor.fromDto(registerDoctorDto);
        doctor.setRole(Role.DOCTOR);
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword())); // encode password before saving it to a database
        doctorRepository.save(doctor);

        SuccessfulDoctorReg successfulDoctorReg = SuccessfulDoctorReg.fromDatabaseToDto(doctor); // wrap in dto before responding

        return ResponseEntity.ok(successfulDoctorReg+"\n"+successfulDoctorReg.SuccesfulRegMessage());

    }
}
