# Guía de entrega MediTrack

1. Abra una terminal en la carpeta del proyecto.
2. Ejecute `mvn clean test`.
3. Capture la terminal completa con el resumen:
   `Tests run`, `Failures: 0`, `Errors: 0` y `BUILD SUCCESS`.
4. Ejecute `mvn spring-boot:run`.
5. En otra terminal ejecute:
   - `curl http://localhost:8080/api/appointments`
   - `curl http://localhost:8080/api/appointments/A1`
6. Capture el código de:
   - `Appointment.java`
   - `AppointmentService.java`
   - `AppointmentController.java`
   - `AppointmentTest.java`
   - `AppointmentServiceTest.java`
7. Muestre el historial con:
   - `git branch -a`
   - `git log --oneline --graph --all --decorate`
8. Suba el repositorio a GitHub y compruebe que sea público.
9. Arme el PDF con portada, capturas y una conclusión breve.
