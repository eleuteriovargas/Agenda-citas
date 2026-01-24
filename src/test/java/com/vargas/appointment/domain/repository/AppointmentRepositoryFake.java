package com.vargas.appointment.domain.repository;

import com.vargas.appointment.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.*;

public class AppointmentRepositoryFake implements AppointmentRepository {

    private final Map<UUID, Appointment> database = new HashMap<>();

    @Override
    public Appointment save(Appointment appointment) {
        Objects.requireNonNull(appointment, "Appointment no puede ser nulo");
        database.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(UUID id) {
        Objects.requireNonNull(id, "ID no puede ser nulo");
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Appointment> findByProfessionalAndTimeRange(
            UUID professionalId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        Objects.requireNonNull(professionalId);
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        return database.values().stream()
                .filter(a -> a.getProfessionalId().equals(professionalId))
                .filter(a -> {
                    LocalDateTime existingStart = a.getStartTime();
                    LocalDateTime existingEnd =
                            a.getStartTime().plusMinutes(a.getDurationMinutes());

                    return existingStart.isBefore(end)
                            && start.isBefore(existingEnd);
                })
                .toList();
    }


    // Métodos auxiliares para tests
    public void clear() {
        database.clear();
    }

    public int count() {
        return database.size();
    }
}