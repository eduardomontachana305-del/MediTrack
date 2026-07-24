package com.meditrack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa una cita médica.
 *
 * La clase es inmutable: no puede heredarse, todos sus atributos son finales
 * y no expone métodos que permitan cambiar su estado después de crearla.
 */
public final class Appointment {

    private final String id;
    private final String patientName;
    private final String specialty;
    private final Double costUsd;
    private final List<String> notifyEmails;

    public Appointment(
            String id,
            String patientName,
            String specialty,
            Double costUsd,
            List<String> notifyEmails) {

        this.id = id;
        this.patientName = patientName;
        this.specialty = specialty;
        this.costUsd = costUsd;

        // Copia defensiva de entrada: evita que cambios en la lista original
        // modifiquen el estado interno de la cita.
        this.notifyEmails = notifyEmails == null
                ? new ArrayList<>()
                : new ArrayList<>(notifyEmails);
    }

    public String getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Double getCostUsd() {
        return costUsd;
    }

    public List<String> getNotifyEmails() {
        // Copia defensiva de salida y lista de solo lectura:
        // el consumidor no puede modificar la colección interna.
        return Collections.unmodifiableList(new ArrayList<>(notifyEmails));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Appointment other)) {
            return false;
        }

        return Objects.equals(id, other.id)
                && Objects.equals(patientName, other.patientName)
                && Objects.equals(specialty, other.specialty)
                && Objects.equals(costUsd, other.costUsd)
                && Objects.equals(notifyEmails, other.notifyEmails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientName, specialty, costUsd, notifyEmails);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", patientName='" + patientName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", costUsd=" + costUsd +
                ", notifyEmails=" + notifyEmails +
                '}';
    }
}
