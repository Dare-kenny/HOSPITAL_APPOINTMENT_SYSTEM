package com.example.HospitalAppointmentSystem.configurations;

import com.example.HospitalAppointmentSystem.configurations.jwtConfig.JwtAuthFilter;
import com.example.HospitalAppointmentSystem.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ // enables passwords to save to the database in an encoded format
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // enables config to use my own customUserDetails


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){ // loads my customUserDetails
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // to enable to access "POST" endpoints without needing a csrf token
                .authorizeHttpRequests(auth ->
                        auth    // for role based access
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/patient/**").hasRole("PATIENT")
                                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                                .requestMatchers("/hod/**").hasRole("HOD")
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()) // use my customUserDetails to load user credentials from the database
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //check for valid jwt token before validating UsernameAndPassword
                .build();
    }
}
