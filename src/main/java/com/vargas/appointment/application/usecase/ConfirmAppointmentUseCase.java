package com.vargas.appointment.application.usecase;

import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ConfirmAppointmentUseCase {

    private final AppointmentRepository repository;

    public ConfirmAppointmentUseCase(AppointmentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Appointment execute(UUID appointmentId) {
        Objects.requireNonNull(appointmentId, "El ID de la cita no puede ser nulo");

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la cita con ID: " + appointmentId
                ));

        appointment.confirm();

        return repository.save(appointment);
    }
}