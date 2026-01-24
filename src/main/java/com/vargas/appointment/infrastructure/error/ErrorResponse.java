package com.vargas.appointment.infrastructure.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ErrorResponse {

    private final String mensaje;
    private final int status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    private final String path;

    public ErrorResponse(String mensaje, int status, String path) {
        this.mensaje = Objects.requireNonNull(mensaje, "El mensaje no puede ser nulo");
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public ErrorResponse(String mensaje, int status) {
        this(mensaje, status, null);
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}