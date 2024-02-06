package com.picpaysimplificado.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long payer_id, Long payee_id) {
    @Override
    public BigDecimal value() {
        return value;
    }

    @Override
    public Long payer_id() {
        return payer_id;
    }

    @Override
    public Long payee_id() {
        return payee_id;
    }
}
