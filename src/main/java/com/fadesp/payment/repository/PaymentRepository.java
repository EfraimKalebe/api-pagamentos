package com.fadesp.payment.repository;

import com.fadesp.payment.model.Payment;
import com.fadesp.payment.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCodigoDebito(Integer codigoDebito);

    List<Payment> findByCpfCnpjPagador(String cpfCnpjPagador);

    List<Payment> findByStatus(StatusPagamento status);
}
