package com.motocitas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client client;

    public String plate;
    public String brand;
    public String model;
    public Integer year;
    public Boolean active = true;
    public LocalDateTime createdAt = LocalDateTime.now();

    public Motorcycle() {}

    public Motorcycle(Client client, String plate, String brand, String model, Integer year) {
        this.client = client;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
}
