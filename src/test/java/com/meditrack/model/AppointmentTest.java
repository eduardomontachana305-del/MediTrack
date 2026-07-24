package com.meditrack.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class AppointmentTest {

    @Test
    public void getters_datosDelConstructor_debenRetornarLosMismosValores() {
        // Arrange
        List<String> emails = List.of(
                "fabricio.montachana@gmail.com",
                "familiar@gmail.com"
        );

        Appointment appointment = new Appointment(
                "A10",
                "Fabricio Montachana",
                "Oftalmología",
                40.00,
                emails
        );

        // Act
        String idObtenido = appointment.getId();
        String pacienteObtenido = appointment.getPatientName();
        String especialidadObtenida = appointment.getSpecialty();
        Double costoObtenido = appointment.getCostUsd();
        List<String> emailsObtenidos = appointment.getNotifyEmails();

        // Assert
        assertEquals("A10", idObtenido);
        assertEquals("Fabricio Montachana", pacienteObtenido);
        assertEquals("Oftalmología", especialidadObtenida);
        assertEquals(Double.valueOf(40.00), costoObtenido);
        assertEquals(emails, emailsObtenidos);
    }

    @Test
    public void constructor_listaOriginalModificada_debeConservarCopiaDefensiva() {
        // Arrange
        List<String> emailsOriginales = new ArrayList<>();
        emailsOriginales.add("fabricio.montachana@gmail.com");

        Appointment appointment = new Appointment(
                "A11",
                "Hugo Montachana",
                "Traumatología",
                35.00,
                emailsOriginales
        );

        // Act
        emailsOriginales.add("correo.agregado@gmail.com");
        List<String> emailsDelObjeto = appointment.getNotifyEmails();

        // Assert
        assertEquals(1, emailsDelObjeto.size());
        assertNotSame(emailsOriginales, emailsDelObjeto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getterNotifyEmails_intentoDeModificar_debeRechazarElCambio() {
        // Arrange
        Appointment appointment = new Appointment(
                "A12",
                "Rosario Aldana",
                "Nutrición",
                28.00,
                List.of("rosario.aldana@gmail.com")
        );

        // Act
        List<String> emailsSoloLectura = appointment.getNotifyEmails();

        // Assert
        emailsSoloLectura.add("correo.nuevo@gmail.com");
    }
}
