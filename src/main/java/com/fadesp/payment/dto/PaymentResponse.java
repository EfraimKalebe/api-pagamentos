package com.fadesp.payment.dto;

import com.fadesp.payment.model.MetodoPagamento;
import com.fadesp.payment.model.Payment;
import com.fadesp.payment.model.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private Integer codigoDebito;
    private String cpfCnpjPagador;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private BigDecimal valorPagamento;
    private StatusPagamento status;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.codigoDebito = payment.getCodigoDebito();
        this.cpfCnpjPagador = payment.getCpfCnpjPagador();
        this.metodoPagamento = payment.getMetodoPagamento();
        this.numeroCartao = payment.getNumeroCartao();
        this.valorPagamento = payment.getValorPagamento();
        this.status = payment.getStatus();
        this.ativo = payment.getAtivo();
        this.dataCriacao = payment.getDataCriacao();
        this.dataAtualizacao = payment.getDataAtualizacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(Integer codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getCpfCnpjPagador() {
        return cpfCnpjPagador;
    }

    public void setCpfCnpjPagador(String cpfCnpjPagador) {
        this.cpfCnpjPagador = cpfCnpjPagador;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public BigDecimal getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(BigDecimal valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
