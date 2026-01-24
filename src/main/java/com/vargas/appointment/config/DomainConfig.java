package com.vargas.appointment.config;

import com.vargas.appointment.domain.repository.AppointmentRepository;
import com.vargas.appointment.domain.service.AppointmentDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public AppointmentDomainService appointmentDomainService(
            AppointmentRepository appointmentRepository
    ) {
        return new AppointmentDomainService(appointmentRepository);
    }
}