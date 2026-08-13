package com.motocitas.repository;

import com.motocitas.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByScheduledAtBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByClientIdAndActiveTrue(Long clientId);
    List<Appointment> findByMotorcycleIdAndActiveTrue(Long motorcycleId);
    List<Appointment> findByStatusAndActiveTrue(String status);
    List<Appointment> findByServiceItemIdAndActiveTrue(Long serviceId);
}
