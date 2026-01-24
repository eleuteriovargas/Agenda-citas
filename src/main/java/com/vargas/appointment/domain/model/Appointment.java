package com.vargas.appointment.domain.model;

import com.vargas.appointment.domain.valueobject.AppointmentStatus;
import com.vargas.appointment.domain.valueobject.AppointmentTime;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Appointment {

    private final UUID id;
    private final UUID businessId;
    private final UUID clientId;
    private final UUID professionalId;
    private final UUID serviceId;

    private AppointmentTime appointmentTime;
    private AppointmentStatus status;

    // Constructor para crear nueva cita
    public Appointment(
            UUID id,
            UUID businessId,
            UUID clientId,
            UUID professionalId,
            UUID serviceId,
            LocalDateTime startTime,
            int durationMinutes
    ) {
        this.id = Objects.requireNonNull(id, "El ID no puede ser nulo");
        this.businessId = Objects.requireNonNull(businessId, "El ID del negocio no puede ser nulo");
        this.clientId = Objects.requireNonNull(clientId, "El ID del cliente no puede ser nulo");
        this.professionalId = Objects.requireNonNull(professionalId, "El ID del profesional no puede ser nulo");
        this.serviceId = Objects.requireNonNull(serviceId, "El ID del servicio no puede ser nulo");
        this.appointmentTime = new AppointmentTime(startTime, durationMinutes);
        this.status = AppointmentStatus.AGENDADO;
    }

    // Constructor para reconstruir desde persistencia
    public Appointment(
            UUID id,
            UUID businessId,
            UUID clientId,
            UUID professionalId,
            UUID serviceId,
            LocalDateTime startTime,
            int durationMinutes,
            AppointmentStatus status
    ) {
        this.id = Objects.requireNonNull(id, "El ID no puede ser nulo");
        this.businessId = Objects.requireNonNull(businessId, "El ID del negocio no puede ser nulo");
        this.clientId = Objects.requireNonNull(clientId, "El ID del cliente no puede ser nulo");
        this.professionalId = Objects.requireNonNull(professionalId, "El ID del profesional no puede ser nulo");
        this.serviceId = Objects.requireNonNull(serviceId, "El ID del servicio no puede ser nulo");
        this.appointmentTime = new AppointmentTime(startTime, durationMinutes);
        this.status = Objects.requireNonNull(status, "El estado no puede ser nulo");
    }

    // ==================== Reglas de negocio ====================

    public void confirm() {
        if (this.status != AppointmentStatus.AGENDADO) {
            throw new IllegalStateException(
                    "Solo se pueden confirmar citas en estado AGENDADO. Estado actual: " + this.status
            );
        }
        this.status = AppointmentStatus.CONFIRMADO;
    }

    public void cancel() {
        if (this.status == AppointmentStatus.COMPLETADO) {
            throw new IllegalStateException("No se pueden cancelar citas completadas");
        }
        if (this.status == AppointmentStatus.CANCELADO) {
            throw new IllegalStateException("La cita ya está cancelada");
        }
        this.status = AppointmentStatus.CANCELADO;
    }

    public void complete() {
        if (this.status != AppointmentStatus.CONFIRMADO) {
            throw new IllegalStateException(
                    "Solo se pueden completar citas confirmadas. Estado actual: " + this.status
            );
        }
        this.status = AppointmentStatus.COMPLETADO;
    }

    public boolean overlaps(Appointment other) {
        return this.appointmentTime.overlaps(other.appointmentTime);
    }

    public boolean isScheduledFor(UUID professionalId, LocalDateTime start, LocalDateTime end) {
        return this.professionalId.equals(professionalId)
                && this.appointmentTime.isBetween(start, end)
                && this.status != AppointmentStatus.CANCELADO;
    }

    // ==================== Getters ====================

    public UUID getId() {
        return id;
    }

    public UUID getBusinessId() {
        return businessId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public LocalDateTime getStartTime() {
        return appointmentTime.getStartTime();
    }

    public LocalDateTime getEndTime() {
        return appointmentTime.getEndTime();
    }

    public int getDurationMinutes() {
        return appointmentTime.getDurationMinutes();
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
