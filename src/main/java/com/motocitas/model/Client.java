package com.motocitas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String email;
    public String phone;
    public String document;
    public Boolean active = true;
    public LocalDateTime createdAt = LocalDateTime.now();

    public Client() {}

    public Client(String name, String email, String phone, String document) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.document = document;
    }
}
