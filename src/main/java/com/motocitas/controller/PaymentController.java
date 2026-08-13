package com.motocitas.controller;

import com.motocitas.dto.CreatePaymentRequest;
import com.motocitas.model.Payment;
import com.motocitas.repository.PaymentRepository;
import com.motocitas.service.PaymentService;
import com.motocitas.service.PaymentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    @Autowired
    private PaymentRepository repo;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentDtoService paymentDtoService;

    @GetMapping
    public List<Payment> list() { 
        return repo.findAll(); 
    }

    @PostMapping
    public Payment create(@RequestBody CreatePaymentRequest req) {
        Payment payment = paymentDtoService.buildPaymentFromRequest(req);
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        repo.findById(id).ifPresent(p -> { p.active = false; repo.save(p); }); 
    }
}
