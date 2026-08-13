package com.motocitas.controller;

import com.motocitas.model.ServiceItem;
import com.motocitas.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {
    @Autowired
    private ServiceRepository repo;

    @GetMapping
    public List<ServiceItem> list(@RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) return repo.findByNameContainingIgnoreCase(q);
        return repo.findAll();
    }

    @PostMapping
    public ServiceItem create(@RequestBody ServiceItem s) { 
        return repo.save(s); 
    }

    @GetMapping("/{id}")
    public ServiceItem getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ServiceItem update(@PathVariable Long id, @RequestBody ServiceItem s) { 
        s.id = id; 
        return repo.save(s); 
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        repo.findById(id).ifPresent(s -> { s.active = false; repo.save(s); }); 
    }
}
