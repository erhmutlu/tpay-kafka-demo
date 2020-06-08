package org.erhanmutlu.asyncpaymentgateway.domain;

import lombok.Getter;
import org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request.PayRequest;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;

import java.math.BigDecimal;

@Getter
public class PayEvent extends IdempotentMessage {

    private final String referenceId;
    private final BigDecimal amount;
    private final String currency;
    private final Integer installment;
    private final String cardUserKey;
    private final String cardKey;

    public PayEvent(PayRequest payRequest) {
        super(payRequest.getPhase().name());
        this.referenceId = payRequest.getReferenceId();
        this.amount = payRequest.getAmount();
        this.currency = payRequest.getCurrency();
        this.installment = payRequest.getInstallment();
        this.cardUserKey = payRequest.getCardUserKey();
        this.cardKey = payRequest.getCardKey();
    }
}
