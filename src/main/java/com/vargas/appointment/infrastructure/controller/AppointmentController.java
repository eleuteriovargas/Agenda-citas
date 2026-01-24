package com.vargas.appointment.infrastructure.controller;

import com.vargas.appointment.application.dto.CreateAppointmentRequest;
import com.vargas.appointment.application.usecase.CancelAppointmentUseCase;
import com.vargas.appointment.application.usecase.ConfirmAppointmentUseCase;
import com.vargas.appointment.application.usecase.CreateAppointmentUseCase;
import com.vargas.appointment.domain.model.Appointment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createUseCase;
    private final ConfirmAppointmentUseCase confirmUseCase;
    private final CancelAppointmentUseCase cancelUseCase;

    public AppointmentController(
            CreateAppointmentUseCase createUseCase,
            ConfirmAppointmentUseCase confirmUseCase,
            CancelAppointmentUseCase cancelUseCase
    ) {
        this.createUseCase = createUseCase;
        this.confirmUseCase = confirmUseCase;
        this.cancelUseCase = cancelUseCase;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = createUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Appointment> confirm(@PathVariable UUID id) {
        Appointment appointment = confirmUseCase.execute(id);
        return ResponseEntity.ok(appointment);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable UUID id) {
        Appointment appointment = cancelUseCase.execute(id);
        return ResponseEntity.ok(appointment);
    }
}
