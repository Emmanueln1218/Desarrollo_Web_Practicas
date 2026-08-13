package com.motocitas.repository;

import com.motocitas.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    List<Motorcycle> findByPlateContainingIgnoreCase(String plate);
    List<Motorcycle> findByClientIdAndActiveTrue(Long clientId);
    List<Motorcycle> findByActiveTrue();
}
