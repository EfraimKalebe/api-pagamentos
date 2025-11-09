package com.fadesp.payment.service;

import com.fadesp.payment.dto.PaymentRequest;
import com.fadesp.payment.dto.PaymentResponse;
import com.fadesp.payment.dto.StatusUpdateRequest;
import com.fadesp.payment.exception.InvalidStatusTransitionException;
import com.fadesp.payment.exception.PaymentNotFoundException;
import com.fadesp.payment.model.Payment;
import com.fadesp.payment.model.StatusPagamento;
import com.fadesp.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setCodigoDebito(request.getCodigoDebito());
        payment.setCpfCnpjPagador(request.getCpfCnpjPagador());
        payment.setMetodoPagamento(request.getMetodoPagamento());
        payment.setNumeroCartao(request.getNumeroCartao());
        payment.setValorPagamento(request.getValorPagamento());

        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment);
    }

    public PaymentResponse updatePaymentStatus(Long id, StatusUpdateRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Pagamento não encontrado"));

        validateStatusTransition(payment.getStatus(), request.getNovoStatus());

        payment.setStatus(request.getNovoStatus());
        Payment updatedPayment = paymentRepository.save(payment);

        return new PaymentResponse(updatedPayment);
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByCodigoDebito(Integer codigoDebito) {
        return paymentRepository.findByCodigoDebito(codigoDebito).stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByCpfCnpj(String cpfCnpjPagador) {
        return paymentRepository.findByCpfCnpjPagador(cpfCnpjPagador).stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByStatus(StatusPagamento status) {
        return paymentRepository.findByStatus(status).stream()
                .map(PaymentResponse::new)
                .collect(Collectors.toList());
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Pagamento não encontrado"));

        if (payment.getStatus() != StatusPagamento.PENDENTE_PROCESSAMENTO) {
            throw new InvalidStatusTransitionException(
                    "Apenas pagamentos com status PENDENTE_PROCESSAMENTO podem ser excluídos");
        }

        payment.setAtivo(false);
        paymentRepository.save(payment);
    }

    private void validateStatusTransition(StatusPagamento currentStatus, StatusPagamento newStatus) {
        if (currentStatus == StatusPagamento.PENDENTE_PROCESSAMENTO) {
            if (newStatus != StatusPagamento.PROCESSADO_SUCESSO &&
                    newStatus != StatusPagamento.PROCESSADO_FALHA) {
                throw new InvalidStatusTransitionException(
                        "Status PENDENTE_PROCESSAMENTO só pode ser alterado para PROCESSADO_SUCESSO ou PROCESSADO_FALHA");
            }
        } else if (currentStatus == StatusPagamento.PROCESSADO_SUCESSO) {
            throw new InvalidStatusTransitionException(
                    "Status PROCESSADO_SUCESSO não pode ser alterado");
        } else if (currentStatus == StatusPagamento.PROCESSADO_FALHA) {
            if (newStatus != StatusPagamento.PENDENTE_PROCESSAMENTO) {
                throw new InvalidStatusTransitionException(
                        "Status PROCESSADO_FALHA só pode ser alterado para PENDENTE_PROCESSAMENTO");
            }
        }
    }
}
