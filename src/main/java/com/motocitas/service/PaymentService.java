package com.motocitas.service;

import com.motocitas.exception.AppException;
import com.motocitas.model.Payment;
import com.motocitas.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        // Validación: Appointment debe ser válido
        if (payment.appointment == null || payment.appointment.id == null) {
            throw new AppException("INVALID_APPOINTMENT", "Cita no válida");
        }

        // Validación: No pagar citas canceladas
        if ("CANCELED".equals(payment.appointment.status)) {
            throw new AppException("CANCELED_APPOINTMENT", "No se puede registrar pago para cita cancelada");
        }

        // Validación: El monto no puede exceder el total de la cita
        if (payment.amount != null && payment.appointment.total != null 
            && payment.amount > payment.appointment.total) {
            throw new AppException("AMOUNT_EXCEEDS_TOTAL", "El monto no puede exceder el total de la cita");
        }

        // Validación: El monto debe ser positivo
        if (payment.amount == null || payment.amount <= 0) {
            throw new AppException("INVALID_AMOUNT", "El monto debe ser mayor a 0");
        }

        // Validación: Método de pago válido
        if (payment.method == null || !List.of("CASH", "CARD", "TRANSFER").contains(payment.method.toUpperCase())) {
            throw new AppException("INVALID_METHOD", "Método de pago no válido. Usar: CASH, CARD, TRANSFER");
        }

        return paymentRepository.save(payment);
    }
}
