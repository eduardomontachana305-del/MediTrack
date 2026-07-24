package com.meditrack.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(String id) {
        super("No se encontró la cita con id: " + id);
    }
}
