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

@DisplayName("Caso de Uso: Confirmar Cita")
class ConfirmAppointmentUseCaseTest {

    private ConfirmAppointmentUseCase confirmUseCase;
    private AppointmentRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepositoryFake();
        confirmUseCase = new ConfirmAppointmentUseCase(repository);
    }

    @Test
    @DisplayName("Debería confirmar una cita agendada")
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
        repository.save(appointment);

        // When
        Appointment confirmed = confirmUseCase.execute(appointment.getId());

        // Then
        assertNotNull(confirmed);
        assertEquals(AppointmentStatus.CONFIRMADO, confirmed.getStatus());
    }

    @Test
    @DisplayName("No debería confirmar cita inexistente")
    void noDeberiaConfirmarCitaInexistente() {
        // Given
        UUID idInexistente = UUID.randomUUID();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> confirmUseCase.execute(idInexistente)
        );

        assertTrue(exception.getMessage().contains("No se encontró la cita"));
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
        repository.save(appointment);
        appointment.confirm(); // Primera confirmación
        repository.save(appointment);

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> confirmUseCase.execute(appointment.getId())
        );

        assertTrue(exception.getMessage().contains("Solo se pueden confirmar citas en estado AGENDADO"));
    }
}