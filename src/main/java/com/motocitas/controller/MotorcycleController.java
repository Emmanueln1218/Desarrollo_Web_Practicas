package com.motocitas.controller;

import com.motocitas.model.Motorcycle;
import com.motocitas.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
@CrossOrigin(origins = "*")
public class MotorcycleController {
    @Autowired
    private MotorcycleRepository repo;

    @GetMapping
    public List<Motorcycle> list(@RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) return repo.findByPlateContainingIgnoreCase(q);
        return repo.findAll();
    }

    @PostMapping
    public Motorcycle create(@RequestBody Motorcycle m) { 
        return repo.save(m); 
    }

    @GetMapping("/{id}")
    public Motorcycle getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Motorcycle update(@PathVariable Long id, @RequestBody Motorcycle m) { 
        m.id = id; 
        return repo.save(m); 
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        repo.findById(id).ifPresent(m -> { m.active = false; repo.save(m); }); 
    }
}
