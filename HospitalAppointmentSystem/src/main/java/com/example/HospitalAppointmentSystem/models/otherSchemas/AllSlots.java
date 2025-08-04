package com.example.HospitalAppointmentSystem.models.otherSchemas;

import com.example.HospitalAppointmentSystem.models.entitySchemas.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AllSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    private String timeSlots;

    @ManyToOne
    private Doctor doctor;

    private boolean isBooked;

    @PrePersist
    @PreUpdate
    private void validateDuration() {
        System.out.println("DEBUG: timeSlots = '" + timeSlots + "'");

        if (timeSlots == null || !timeSlots.matches("\\d{2}:\\d{2}\\s*-\\s*\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("TimeSlots must be in format 'HH:mm - HH:mm'");
        }

        try {
            String[] parts = timeSlots.split("\\s*-\\s*");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid time range format.");
            }

            LocalTime start = LocalTime.parse(parts[0].trim());
            LocalTime end = LocalTime.parse(parts[1].trim());

            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Start time must be before End time");
            }

            if (!Duration.between(start, end).equals(Duration.ofHours(2))) {
                throw new IllegalArgumentException("Slot duration must be exactly 2 hours");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format: " + e.getMessage());
        }
    }



}
