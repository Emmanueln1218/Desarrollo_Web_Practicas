package com.motocitas.dto;

public class CreatePaymentRequest {
    public Long appointmentId;
    public Double amount;
    public String method; // CASH, CARD, TRANSFER

    public CreatePaymentRequest() {}

    public CreatePaymentRequest(Long appointmentId, Double amount, String method) {
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.method = method;
    }
}
