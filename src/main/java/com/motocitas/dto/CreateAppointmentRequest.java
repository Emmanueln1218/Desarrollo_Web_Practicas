package com.motocitas.dto;

import java.time.LocalDateTime;

public class CreateAppointmentRequest {
    public Long clientId;
    public Long motorcycleId;
    public Long serviceId;
    public LocalDateTime scheduledAt;

    public CreateAppointmentRequest() {}

    public CreateAppointmentRequest(Long clientId, Long motorcycleId, Long serviceId, LocalDateTime scheduledAt) {
        this.clientId = clientId;
        this.motorcycleId = motorcycleId;
        this.serviceId = serviceId;
        this.scheduledAt = scheduledAt;
    }
}
