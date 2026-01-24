package com.vargas.appointment.infrastructure.mapper;

import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.infrastructure.entity.AppointmentEntity;

public class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static AppointmentEntity toEntity(Appointment domain) {
        return AppointmentEntity.builder()
                .id(domain.getId())
                .businessId(domain.getBusinessId())
                .clientId(domain.getClientId())
                .professionalId(domain.getProfessionalId())
                .serviceId(domain.getServiceId())
                .startTime(domain.getStartTime())
                .durationMinutes(domain.getDurationMinutes())
                .status(domain.getStatus())
                .build();
    }

    public static Appointment toDomain(AppointmentEntity entity) {
        return new Appointment(
                entity.getId(),
                entity.getBusinessId(),
                entity.getClientId(),
                entity.getProfessionalId(),
                entity.getServiceId(),
                entity.getStartTime(),
                entity.getDurationMinutes(),
                entity.getStatus()
        );
    }
}
