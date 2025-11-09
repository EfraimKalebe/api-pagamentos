package com.fadesp.payment.controller;

import com.fadesp.payment.dto.PaymentRequest;
import com.fadesp.payment.dto.PaymentResponse;
import com.fadesp.payment.dto.StatusUpdateRequest;
import com.fadesp.payment.model.StatusPagamento;
import com.fadesp.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        PaymentResponse response = paymentService.updatePaymentStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPayments(
            @RequestParam(required = false) Integer codigoDebito,
            @RequestParam(required = false) String cpfCnpjPagador,
            @RequestParam(required = false) StatusPagamento status) {

        List<PaymentResponse> payments;

        if (codigoDebito != null) {
            payments = paymentService.getPaymentsByCodigoDebito(codigoDebito);
        } else if (cpfCnpjPagador != null) {
            payments = paymentService.getPaymentsByCpfCnpj(cpfCnpjPagador);
        } else if (status != null) {
            payments = paymentService.getPaymentsByStatus(status);
        } else {
            payments = paymentService.getAllPayments();
        }

        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
