package com.vargas.appointment.domain.repository;

import com.vargas.appointment.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    Optional<Appointment> findById(UUID id);

    List<Appointment> findByProfessionalAndTimeRange(
            UUID professionalId,
            LocalDateTime start,
            LocalDateTime end
    );
}
