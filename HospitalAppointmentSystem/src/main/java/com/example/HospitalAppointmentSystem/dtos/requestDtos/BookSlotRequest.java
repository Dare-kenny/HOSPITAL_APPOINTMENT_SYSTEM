package com.example.HospitalAppointmentSystem.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSlotRequest { // dto wrapper for the patient to request a slot

    private Long slotId;

}
