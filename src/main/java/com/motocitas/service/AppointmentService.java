package com.motocitas.service;

import com.motocitas.exception.AppException;
import com.motocitas.model.Appointment;
import com.motocitas.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        // Validación: Cliente y Moto deben ser válidos
        if (appointment.client == null || appointment.client.id == null) {
            throw new AppException("INVALID_CLIENT", "Cliente no válido");
        }
        if (appointment.motorcycle == null || appointment.motorcycle.id == null) {
            throw new AppException("INVALID_MOTORCYCLE", "Motocicleta no válida");
        }
        if (appointment.serviceItem == null || appointment.serviceItem.id == null) {
            throw new AppException("INVALID_SERVICE", "Servicio no válido");
        }

        // Validación: La moto debe pertenecer al cliente
        if (!appointment.motorcycle.client.id.equals(appointment.client.id)) {
            throw new AppException("MOTO_NOT_BELONG_TO_CLIENT", "La motocicleta no pertenece al cliente");
        }

        // Validación: Evitar citas duplicadas en el mismo horario (ventana de 1 hora)
        LocalDateTime start = appointment.scheduledAt.minusHours(1);
        LocalDateTime end = appointment.scheduledAt.plusHours(1);
        List<Appointment> existing = appointmentRepository.findByScheduledAtBetween(start, end);
        boolean duplicated = existing.stream()
            .anyMatch(a -> a.motorcycle.id.equals(appointment.motorcycle.id) 
                        && a.active 
                        && !a.status.equals("CANCELED"));
        if (duplicated) {
            throw new AppException("DUPLICATE_APPOINTMENT", "Ya existe una cita para esta motocicleta en ese horario");
        }

        // Calcular total
        if (appointment.serviceItem.price != null) {
            appointment.total = appointment.serviceItem.price;
        }

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = appointmentRepository.findById(id)
            .orElseThrow(() -> new AppException("NOT_FOUND", "Cita no encontrada"));

        if (updated.status != null) existing.status = updated.status;
        if (updated.scheduledAt != null) existing.scheduledAt = updated.scheduledAt;
        if (updated.total != null) existing.total = updated.total;

        return appointmentRepository.save(existing);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByScheduledAtBetween(start, end);
    }
}
