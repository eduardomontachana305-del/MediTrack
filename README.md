# MediTrack — Servicio Reactivo de Citas Médicas

Proyecto académico desarrollado con Spring WebFlux, Project Reactor, JUnit 4
y StepVerifier.

## Requisitos

- Java 17
- Maven 3.8 o superior
- Git

## Ejecutar pruebas

```bash
mvn clean test
```

## Ejecutar aplicación

```bash
mvn spring-boot:run
```

## Probar endpoints con curl

```bash
curl http://localhost:8080/api/appointments
curl http://localhost:8080/api/appointments/A1
```

## Regla de negocio

Una cita es válida cuando:

- `costUsd > 0`
- `notifyEmails` contiene al menos un correo

## Ramas académicas

- `feature/modelo`
- `feature/servicio-reactivo`
- `feature/api-rest`
- `feature/pruebas`
