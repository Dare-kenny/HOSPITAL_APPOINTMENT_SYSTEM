package com.example.HospitalAppointmentSystem;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the password: ");
        String rawPassword = scanner.next();
        scanner.close();

        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("HashedPassword: "+encodedPassword);

        // jack.Frost@example.com  password123
        //jane.smith@example.com   securePass456
    }
}
