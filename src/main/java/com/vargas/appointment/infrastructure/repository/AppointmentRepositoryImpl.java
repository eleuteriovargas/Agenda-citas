package com.vargas.appointment.infrastructure.repository;

import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepository;
import com.vargas.appointment.infrastructure.mapper.AppointmentMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final SpringDataAppointmentRepository jpaRepository;

    public AppointmentRepositoryImpl(SpringDataAppointmentRepository jpaRepository) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "JPA Repository no puede ser nulo");
    }

    @Override
    public Appointment save(Appointment appointment) {
        Objects.requireNonNull(appointment, "Appointment no puede ser nulo");
        return AppointmentMapper.toDomain(
                jpaRepository.save(AppointmentMapper.toEntity(appointment))
        );
    }

    @Override
    public Optional<Appointment> findById(UUID id) {
        Objects.requireNonNull(id, "ID no puede ser nulo");
        return jpaRepository.findById(id)
                .map(AppointmentMapper::toDomain);
    }

    @Override
    public List<Appointment> findByProfessionalAndTimeRange(
            UUID professionalId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        Objects.requireNonNull(professionalId, "Professional ID no puede ser nulo");
        Objects.requireNonNull(start, "Start time no puede ser nulo");
        Objects.requireNonNull(end, "End time no puede ser nulo");

        return jpaRepository
                .findByProfessionalIdAndStartTimeBetween(professionalId, start, end)
                .stream()
                .map(AppointmentMapper::toDomain)
                .toList();
    }
}
