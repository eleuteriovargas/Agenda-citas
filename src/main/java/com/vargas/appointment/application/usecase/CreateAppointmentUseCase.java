package com.vargas.appointment.application.usecase;

import com.vargas.appointment.application.dto.CreateAppointmentRequest;
import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepository;
import com.vargas.appointment.domain.service.AppointmentDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class CreateAppointmentUseCase {

    private final AppointmentRepository repository;
    private final AppointmentDomainService domainService;

    public CreateAppointmentUseCase(
            AppointmentRepository repository,
            AppointmentDomainService domainService
    ) {
        this.repository = Objects.requireNonNull(repository);
        this.domainService = Objects.requireNonNull(domainService);
    }

    public Appointment execute(CreateAppointmentRequest request) {
        Objects.requireNonNull(request, "La solicitud no puede ser nula");

        LocalDateTime start = request.startTime;
        LocalDateTime end = start.plusMinutes(request.durationMinutes);

        // Validar disponibilidad del profesional
        domainService.validateAvailability(
                request.professionalId,
                start,
                end
        );

        // Crear la cita
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                request.businessId,
                request.clientId,
                request.professionalId,
                request.serviceId,
                start,
                request.durationMinutes
        );

        return repository.save(appointment);
    }
}