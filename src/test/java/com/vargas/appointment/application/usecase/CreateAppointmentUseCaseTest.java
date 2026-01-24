package com.vargas.appointment.application.usecase;

import com.vargas.appointment.application.dto.CreateAppointmentRequest;
import com.vargas.appointment.domain.model.Appointment;
import com.vargas.appointment.domain.repository.AppointmentRepositoryFake;
import com.vargas.appointment.domain.service.AppointmentDomainService;
import com.vargas.appointment.domain.valueobject.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Caso de Uso: Crear Cita")
class CreateAppointmentUseCaseTest {

    private CreateAppointmentUseCase createUseCase;
    private AppointmentRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepositoryFake();
        AppointmentDomainService domainService = new AppointmentDomainService(repository);
        createUseCase = new CreateAppointmentUseCase(repository, domainService);
    }

    @Test
    @DisplayName("Debería crear una cita correctamente")
    void deberiaCrearUnaCitaCorrectamente() {
        // Given
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.businessId = UUID.randomUUID();
        request.clientId = UUID.randomUUID();
        request.professionalId = UUID.randomUUID();
        request.serviceId = UUID.randomUUID();
        request.startTime = LocalDateTime.now().plusDays(1);
        request.durationMinutes = 30;

        // When
        Appointment appointment = createUseCase.execute(request);

        // Then
        assertNotNull(appointment);
        assertNotNull(appointment.getId());
        assertEquals(AppointmentStatus.AGENDADO, appointment.getStatus());
        assertEquals(request.clientId, appointment.getClientId());
        assertEquals(request.professionalId, appointment.getProfessionalId());
        assertEquals(30, appointment.getDurationMinutes());
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("No debería permitir citas traslapadas para el mismo profesional")
    void noDeberiaPermitirCitasTraslapadas() {
        // Given - Primera cita
        CreateAppointmentRequest primeraRequest = new CreateAppointmentRequest();
        UUID professionalId = UUID.randomUUID();
        LocalDateTime startTime = LocalDateTime.of(2026, 2, 1, 10, 0);

        primeraRequest.businessId = UUID.randomUUID();
        primeraRequest.clientId = UUID.randomUUID();
        primeraRequest.professionalId = professionalId;
        primeraRequest.serviceId = UUID.randomUUID();
        primeraRequest.startTime = startTime;
        primeraRequest.durationMinutes = 60;

        createUseCase.execute(primeraRequest);

        // When - Segunda cita traslapada
        CreateAppointmentRequest segundaRequest = new CreateAppointmentRequest();
        segundaRequest.businessId = UUID.randomUUID();
        segundaRequest.clientId = UUID.randomUUID();
        segundaRequest.professionalId = professionalId;
        segundaRequest.serviceId = UUID.randomUUID();
        segundaRequest.startTime = startTime.plusMinutes(30); // Traslape
        segundaRequest.durationMinutes = 30;

        // Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> createUseCase.execute(segundaRequest)
        );

        assertTrue(exception.getMessage().contains("no está disponible"));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Debería permitir citas consecutivas sin traslape")
    void deberiaPermitirCitasConsecutivas() {
        // Given
        UUID professionalId = UUID.randomUUID();
        LocalDateTime startTime = LocalDateTime.of(2026, 2, 1, 10, 0);

        CreateAppointmentRequest primera = new CreateAppointmentRequest();
        primera.businessId = UUID.randomUUID();
        primera.clientId = UUID.randomUUID();
        primera.professionalId = professionalId;
        primera.serviceId = UUID.randomUUID();
        primera.startTime = startTime;
        primera.durationMinutes = 30;

        CreateAppointmentRequest segunda = new CreateAppointmentRequest();
        segunda.businessId = UUID.randomUUID();
        segunda.clientId = UUID.randomUUID();
        segunda.professionalId = professionalId;
        segunda.serviceId = UUID.randomUUID();
        segunda.startTime = startTime.plusMinutes(30); // Justo después
        segunda.durationMinutes = 30;

        // When
        Appointment cita1 = createUseCase.execute(primera);
        Appointment cita2 = createUseCase.execute(segunda);

        // Then
        assertNotNull(cita1);
        assertNotNull(cita2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Debería validar request nulo")
    void deberiaValidarRequestNulo() {
        // When & Then
        assertThrows(
                NullPointerException.class,
                () -> createUseCase.execute(null)
        );
    }
}