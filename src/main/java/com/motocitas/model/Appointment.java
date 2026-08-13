package com.motocitas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client client;

    @ManyToOne
    @JoinColumn(name = "motorcycle_id")
    public Motorcycle motorcycle;

    @ManyToOne
    @JoinColumn(name = "service_id")
    public ServiceItem serviceItem;

    public LocalDateTime scheduledAt;
    public String status = "PENDING"; // PENDING, COMPLETED, CANCELED
    public Double total = 0.0;
    public Boolean active = true;
    public LocalDateTime createdAt = LocalDateTime.now();

    public Appointment() {}

    public Appointment(Client client, Motorcycle motorcycle, ServiceItem serviceItem, LocalDateTime scheduledAt) {
        this.client = client;
        this.motorcycle = motorcycle;
        this.serviceItem = serviceItem;
        this.scheduledAt = scheduledAt;
    }
}
