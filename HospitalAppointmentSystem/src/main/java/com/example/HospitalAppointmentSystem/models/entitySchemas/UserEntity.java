package com.example.HospitalAppointmentSystem.models.entitySchemas;

import com.example.HospitalAppointmentSystem.models.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter @Setter
public abstract class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email(message = "email should be valid")
    @Column(unique = true , nullable = false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRole() {
        return String.valueOf(role);
    }

}
