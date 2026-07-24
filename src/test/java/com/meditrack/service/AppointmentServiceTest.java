package com.meditrack.service;

import com.meditrack.exception.AppointmentNotFoundException;
import com.meditrack.model.Appointment;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class AppointmentServiceTest {

    @Test
    public void getValidAppointments_flujoMixto_debeEmitirSoloLasTresValidas() {
        // Arrange
        AppointmentService service = new AppointmentService();

        // Act
        Flux<Appointment> flujo = service.getValidAppointments();

        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void getValidAppointments_todasInvalidas_debeEmitirLaCitaGenerica() {
        // Arrange
        Flux<Appointment> citasInvalidas = Flux.just(
                new Appointment(
                        "I1",
                        "Paciente Uno",
                        "Cardiología",
                        0.00,
                        List.of("uno@email.com")
                ),
                new Appointment(
                        "I2",
                        "Paciente Dos",
                        "Pediatría",
                        20.00,
                        List.of()
                )
        );

        AppointmentService service = new AppointmentService(citasInvalidas);

        // Act
        Flux<Appointment> flujo = service.getValidAppointments();

        // Assert
        StepVerifier.create(flujo)
                .expectNextMatches(appointment ->
                        "DEFAULT".equals(appointment.getId())
                                && "SIN ESPECIALIDAD".equals(appointment.getSpecialty())
                )
                .verifyComplete();
    }

    @Test
    public void findById_idInexistente_debeFinalizarConError() {
        // Arrange
        AppointmentService service = new AppointmentService();

        // Act y Assert
        StepVerifier.create(service.findById("A99"))
                .verifyError(AppointmentNotFoundException.class);
    }

    @Test
    public void findById_idExistente_debeEmitirLaCitaSolicitada() {
        // Arrange
        AppointmentService service = new AppointmentService();

        // Act y Assert
        StepVerifier.create(service.findById("A1"))
                .expectNextMatches(appointment ->
                        "A1".equals(appointment.getId())
                                && "CARDIOLOGÍA".equals(appointment.getSpecialty())
                )
                .verifyComplete();
    }
}
