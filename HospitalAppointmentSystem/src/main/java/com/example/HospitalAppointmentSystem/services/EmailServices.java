package com.example.HospitalAppointmentSystem.services;

import com.example.HospitalAppointmentSystem.models.otherSchemas.AllSlots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentConfirmation(String name, String to , AllSlots slot){ // service to send confirmation email to the patient
        String subject = "Appointment confirmed";
        String text = "Dear "+name+",\n Your Appointmenthas been booked for "+slot.getDay()+" from "+slot.getTimeSlots()+" with "+slot.getDoctor().getName()+" .";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
    }
}
