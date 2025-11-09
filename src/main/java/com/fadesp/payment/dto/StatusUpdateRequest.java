package com.fadesp.payment.dto;

import com.fadesp.payment.model.StatusPagamento;

import javax.validation.constraints.NotNull;

public class StatusUpdateRequest {

    @NotNull(message = "Novo status é obrigatório")
    private StatusPagamento novoStatus;

    public StatusPagamento getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(StatusPagamento novoStatus) {
        this.novoStatus = novoStatus;
    }
}
