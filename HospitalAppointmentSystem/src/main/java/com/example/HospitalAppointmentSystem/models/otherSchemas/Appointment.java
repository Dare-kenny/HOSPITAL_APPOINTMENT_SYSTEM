package com.example.HospitalAppointmentSystem.models.otherSchemas;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import com.example.HospitalAppointmentSystem.models.entitySchemas.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @OneToOne
    private AllSlots slot;

    @ManyToOne
    private Doctor doctor;


}
