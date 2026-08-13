package com.motocitas.controller;

import com.motocitas.model.Client;
import com.motocitas.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> list(@RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) return clientRepository.findByNameContainingIgnoreCase(q);
        return clientRepository.findAll();
    }

    @PostMapping
    public Client create(@RequestBody Client c) {
        return clientRepository.save(c);
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client c) {
        c.id = id;
        return clientRepository.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientRepository.findById(id).ifPresent(c -> { c.active = false; clientRepository.save(c); });
    }
}
