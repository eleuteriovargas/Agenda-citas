package com.vargas.appointment.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateAppointmentRequest {

    @NotNull(message = "El ID del negocio es requerido")
    public UUID businessId;

    @NotNull(message = "El ID del cliente es requerido")
    public UUID clientId;

    @NotNull(message = "El ID del profesional es requerido")
    public UUID professionalId;

    @NotNull(message = "El ID del servicio es requerido")
    public UUID serviceId;

    @NotNull(message = "La hora de inicio es requerida")
    public LocalDateTime startTime;

    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    public int durationMinutes;
}