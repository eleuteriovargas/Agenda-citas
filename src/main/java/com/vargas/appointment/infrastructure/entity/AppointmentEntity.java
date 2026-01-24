package com.vargas.appointment.infrastructure.entity;

import com.vargas.appointment.domain.valueobject.AppointmentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "business_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID businessId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "client_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID clientId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "professional_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID professionalId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "service_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID serviceId;

    @Column(name = "start_time", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime startTime;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AppointmentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

    // Constructor sin argumentos para JPA
    AppointmentEntity() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ==================== Builder Pattern ====================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AppointmentEntity entity = new AppointmentEntity();

        public Builder id(UUID id) {
            entity.id = id;
            return this;
        }

        public Builder businessId(UUID businessId) {
            entity.businessId = businessId;
            return this;
        }

        public Builder clientId(UUID clientId) {
            entity.clientId = clientId;
            return this;
        }

        public Builder professionalId(UUID professionalId) {
            entity.professionalId = professionalId;
            return this;
        }

        public Builder serviceId(UUID serviceId) {
            entity.serviceId = serviceId;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            entity.startTime = startTime;
            return this;
        }

        public Builder durationMinutes(int durationMinutes) {
            entity.durationMinutes = durationMinutes;
            return this;
        }

        public Builder status(AppointmentStatus status) {
            entity.status = status;
            return this;
        }

        public AppointmentEntity build() {
            return entity;
        }
    }

    // ==================== Getters (sin setters públicos) ====================

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
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}