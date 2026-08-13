package com.motocitas.repository;

import com.motocitas.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceItem, Long> {
    List<ServiceItem> findByNameContainingIgnoreCase(String name);
    List<ServiceItem> findByActiveTrue();
}
