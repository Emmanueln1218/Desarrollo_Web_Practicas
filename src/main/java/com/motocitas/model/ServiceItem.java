package com.motocitas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public Double price = 0.0;
    public Boolean active = true;

    public ServiceItem() {}

    public ServiceItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
