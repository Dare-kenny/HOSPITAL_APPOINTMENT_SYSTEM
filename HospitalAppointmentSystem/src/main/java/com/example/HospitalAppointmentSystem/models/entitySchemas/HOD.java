package com.example.HospitalAppointmentSystem.models.entitySchemas;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "hod")
public class HOD extends UserEntity {
    private String department;
}
