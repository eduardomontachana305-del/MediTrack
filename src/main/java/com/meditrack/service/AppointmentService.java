package com.meditrack.service;

import com.meditrack.exception.AppointmentNotFoundException;
import com.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Service
public class AppointmentService {

    private final Flux<Appointment> appointmentSource;

    /**
     * Constructor usado por la aplicación.
     * Crea cinco citas en memoria: tres válidas y dos inválidas.
     */
    public AppointmentService() {
        this(Flux.just(
                new Appointment(
                        "A1",
                        "Rosario Aldana",
                        "Cardiología",
                        45.00,
                        List.of("Rosario.aldana@gmail.com")
                ),
                new Appointment(
                        "A2",
                        "Daniel Montachana",
                        "Pediatría",
                        30.00,
                        List.of("daniel.montachana@gmail.com", "familiar@gmail.com")
                ),
                new Appointment(
                        "A3",
                        "Norma Toaza",
                        "Dermatología",
                        55.50,
                        List.of("norma.toaza@gmail.com")
                ),
                new Appointment(
                        "A4",
                        "Luis Chicaiza",
                        "Neurología",
                        0.00,
                        List.of("luis.chicaiza@gmail.com")
                ),
                new Appointment(
                        "A5",
                        "Josue Aldana",
                        "Medicina General",
                        25.00,
                        List.of()
                )
        ));
    }

    /**
     * Constructor con visibilidad de paquete, utilizado por las pruebas para
     * controlar el flujo de entrada sin cambiar el comportamiento productivo.
     */
    AppointmentService(Flux<Appointment> appointmentSource) {
        this.appointmentSource = appointmentSource;
    }

    public Flux<Appointment> getValidAppointments() {
        return appointmentSource
                // filter aplica la regla de negocio sin bloquear:
                // solo continúan las citas con costo positivo y al menos un correo.
                .filter(appointment ->
                        appointment.getCostUsd() != null
                                && appointment.getCostUsd() > 0
                                && !appointment.getNotifyEmails().isEmpty()
                )

                // map transforma cada elemento válido y conserva el flujo reactivo.
                // Como Appointment es inmutable, se crea una nueva instancia.
                .map(appointment -> new Appointment(
                        appointment.getId(),
                        appointment.getPatientName(),
                        appointment.getSpecialty().toUpperCase(Locale.ROOT),
                        appointment.getCostUsd(),
                        appointment.getNotifyEmails()
                ))

                // defaultIfEmpty entrega una respuesta controlada cuando ninguna
                // cita supera el filtro, evitando devolver un flujo sin elementos.
                .defaultIfEmpty(genericAppointment());
    }

    public Mono<Appointment> findById(String id) {
        return getValidAppointments()
                // filter localiza de forma reactiva la cita solicitada.
                .filter(appointment -> appointment.getId().equalsIgnoreCase(id))

                // next convierte el primer elemento encontrado de Flux a Mono.
                .next()

                // switchIfEmpty transforma la ausencia de datos en una señal de error,
                // sin usar block() ni extraer el valor fuera del flujo reactivo.
                .switchIfEmpty(Mono.error(new AppointmentNotFoundException(id)));
    }

    private Appointment genericAppointment() {
        return new Appointment(
                "DEFAULT",
                "Paciente no disponible",
                "SIN ESPECIALIDAD",
                1.00,
                List.of("notificaciones@meditrack.local")
        );
    }
}
