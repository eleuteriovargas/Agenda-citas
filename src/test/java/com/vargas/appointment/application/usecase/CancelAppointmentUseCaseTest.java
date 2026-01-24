package com.vargas.appointment.application.usecase;

import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepositoryFake;
import com.vargas.appointment.domain.valueobject.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Caso de Uso: Cancelar Cita")
class CancelAppointmentUseCaseTest {

    private CancelAppointmentUseCase cancelUseCase;
    private AppointmentRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepositoryFake();
        cancelUseCase = new CancelAppointmentUseCase(repository);
    }

    @Test
    @DisplayName("Debería cancelar una cita agendada")
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
        repository.save(appointment);

        // When
        Appointment cancelled = cancelUseCase.execute(appointment.getId());

        // Then
        assertNotNull(cancelled);
        assertEquals(AppointmentStatus.CANCELADO, cancelled.getStatus());
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
                LocalDateTime.now().minusDays(1),
                30,
                AppointmentStatus.COMPLETADO
        );
        repository.save(appointment);

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> cancelUseCase.execute(appointment.getId())
        );

        assertTrue(exception.getMessage().contains("No se pueden cancelar citas completadas"));
    }
}