package com.motocitas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    public Appointment appointment;

    public Double amount;
    public String method; // CASH, CARD, TRANSFER
    public LocalDateTime paidAt = LocalDateTime.now();
    public Boolean active = true;

    public Payment() {}

    public Payment(Appointment appointment, Double amount, String method) {
        this.appointment = appointment;
        this.amount = amount;
        this.method = method;
    }
}
