package com.motocitas.service;

import com.motocitas.dto.CreatePaymentRequest;
import com.motocitas.exception.AppException;
import com.motocitas.model.Appointment;
import com.motocitas.model.Payment;
import com.motocitas.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDtoService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Payment buildPaymentFromRequest(CreatePaymentRequest req) {
        Appointment appointment = appointmentRepository.findById(req.appointmentId)
            .orElseThrow(() -> new AppException("APPOINTMENT_NOT_FOUND", "Cita no encontrada"));

        Payment payment = new Payment();
        payment.appointment = appointment;
        payment.amount = req.amount;
        payment.method = req.method;

        return payment;
    }
}
