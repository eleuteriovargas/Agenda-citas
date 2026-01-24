package com.vargas.appointment.domain.model;

import com.vargas.appointment.domain.valueobject.AppointmentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Entidad de Dominio: Appointment")
class AppointmentTest {

    @Test
    @DisplayName("Debería crear cita con estado AGENDADO por defecto")
    void deberiaCrearCitaConEstadoAgendado() {
        // When
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(1),
                30
        );

        // Then
        assertEquals(AppointmentStatus.AGENDADO, appointment.getStatus());
    }

    @Test
    @DisplayName("Debería confirmar cita agendada")
    void deberiaConfirmarCitaAgendada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(1),
                30
        );

        // When
        appointment.confirm();

        // Then
        assertEquals(AppointmentStatus.CONFIRMADO, appointment.getStatus());
    }

    @Test
    @DisplayName("No debería confirmar cita ya confirmada")
    void noDeberiaConfirmarCitaYaConfirmada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(1),
                30
        );
        appointment.confirm();

        // When & Then
        assertThrows(IllegalStateException.class, appointment::confirm);
    }

    @Test
    @DisplayName("Debería completar cita confirmada")
    void deberiaCompletarCitaConfirmada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().minusHours(1),
                30
        );
        appointment.confirm();

        // When
        appointment.complete();

        // Then
        assertEquals(AppointmentStatus.COMPLETADO, appointment.getStatus());
    }

    @Test
    @DisplayName("No debería completar cita no confirmada")
    void noDeberiaCompletarCitaNoConfirmada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                30
        );

        // When & Then
        assertThrows(IllegalStateException.class, appointment::complete);
    }

    @Test
    @DisplayName("Debería cancelar cita agendada")
    void deberiaCancelarCitaAgendada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(1),
                30
        );

        // When
        appointment.cancel();

        // Then
        assertEquals(AppointmentStatus.CANCELADO, appointment.getStatus());
    }

    @Test
    @DisplayName("No debería cancelar cita completada")
    void noDeberiaCancelarCitaCompletada() {
        // Given
        Appointment appointment = new Appointment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now().minusHours(1),
                30,
                AppointmentStatus.COMPLETADO
        );

        // When & Then
        assertThrows(IllegalStateException.class, appointment::cancel);
    }

    @Test
    @DisplayName("Debería validar IDs no nulos en constructor")
    void deberiaValidarIDsNoNulos() {
        // When & Then
        assertThrows(
                NullPointerException.class,
                () -> new Appointment(
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        LocalDateTime.now(),
                        30
                )
        );
    }
}