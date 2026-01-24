package com.vargas.appointment.domain.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public class AppointmentTime {

    private final LocalDateTime startTime;
    private final int durationMinutes;

    public AppointmentTime(LocalDateTime startTime, int durationMinutes) {
        this.startTime = Objects.requireNonNull(startTime, "La hora de inicio no puede ser nula");

        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos");
        }
        if (durationMinutes > 480) { // 8 horas máximo
            throw new IllegalArgumentException("La duración no puede exceder 480 minutos (8 horas)");
        }

        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }

    public boolean overlaps(AppointmentTime other) {
        return this.startTime.isBefore(other.getEndTime())
                && other.startTime.isBefore(this.getEndTime());
    }

    public boolean isBetween(LocalDateTime start, LocalDateTime end) {
        return !this.startTime.isBefore(start) && this.startTime.isBefore(end);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentTime)) return false;
        AppointmentTime that = (AppointmentTime) o;
        return durationMinutes == that.durationMinutes &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, durationMinutes);
    }
}
