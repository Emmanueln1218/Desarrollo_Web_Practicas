package com.motocitas.controller;

import com.motocitas.dto.CreateAppointmentRequest;
import com.motocitas.model.Appointment;
import com.motocitas.repository.AppointmentRepository;
import com.motocitas.service.AppointmentService;
import com.motocitas.service.AppointmentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    @Autowired
    private AppointmentRepository repo;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentDtoService appointmentDtoService;

    @GetMapping
    public List<Appointment> list() { 
        return repo.findAll(); 
    }

    @PostMapping
    public Appointment create(@RequestBody CreateAppointmentRequest req) {
        Appointment appointment = appointmentDtoService.buildAppointmentFromRequest(req);
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/{id}")
    public Appointment getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment a) {
        return appointmentService.updateAppointment(id, a);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        repo.findById(id).ifPresent(a -> { a.active = false; repo.save(a); }); 
    }
}
