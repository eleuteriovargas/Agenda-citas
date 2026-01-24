package com.vargas.appointment.infrastructure.repository;

import com.vargas.appointment.infrastructure.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataAppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    List<AppointmentEntity> findByProfessionalIdAndStartTimeBetween(
            UUID professionalId,
            LocalDateTime start,
            LocalDateTime end
    );
}
