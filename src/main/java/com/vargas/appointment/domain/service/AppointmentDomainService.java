package com.vargas.appointment.domain.service;

import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AppointmentDomainService {

    private final AppointmentRepository repository;

    public AppointmentDomainService(AppointmentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "El repositorio no puede ser nulo");
    }

    public void validateAvailability(
            UUID professionalId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        Objects.requireNonNull(professionalId, "El ID del profesional no puede ser nulo");
        Objects.requireNonNull(start, "La hora de inicio no puede ser nula");
        Objects.requireNonNull(end, "La hora de fin no puede ser nula");

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }

        List<Appointment> overlapping = repository.findByProfessionalAndTimeRange(
                professionalId,
                start,
                end
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException(
                    "El profesional no está disponible. Existe(n) " + overlapping.size() +
                            " cita(s) en conflicto"
            );
        }
    }
}