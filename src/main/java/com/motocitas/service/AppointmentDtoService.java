package com.motocitas.service;

import com.motocitas.dto.CreateAppointmentRequest;
import com.motocitas.exception.AppException;
import com.motocitas.model.Appointment;
import com.motocitas.model.Client;
import com.motocitas.model.Motorcycle;
import com.motocitas.model.ServiceItem;
import com.motocitas.repository.ClientRepository;
import com.motocitas.repository.MotorcycleRepository;
import com.motocitas.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentDtoService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Appointment buildAppointmentFromRequest(CreateAppointmentRequest req) {
        Client client = clientRepository.findById(req.clientId)
            .orElseThrow(() -> new AppException("CLIENT_NOT_FOUND", "Cliente no encontrado"));
        
        Motorcycle motorcycle = motorcycleRepository.findById(req.motorcycleId)
            .orElseThrow(() -> new AppException("MOTORCYCLE_NOT_FOUND", "Motocicleta no encontrada"));
        
        ServiceItem service = serviceRepository.findById(req.serviceId)
            .orElseThrow(() -> new AppException("SERVICE_NOT_FOUND", "Servicio no encontrado"));

        Appointment appointment = new Appointment();
        appointment.client = client;
        appointment.motorcycle = motorcycle;
        appointment.serviceItem = service;
        appointment.scheduledAt = req.scheduledAt;

        return appointment;
    }
}
