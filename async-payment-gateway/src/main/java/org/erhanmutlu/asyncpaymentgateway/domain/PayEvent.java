package org.erhanmutlu.asyncpaymentgateway.domain;

import lombok.Getter;
import org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request.PayRequest;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;

import java.math.BigDecimal;

@Getter
public class PayEvent extends IdempotentMessage {

    private String referenceId;
    private String phase;
    private BigDecimal amount;
    private String currency;
    private Integer installment;
    private String cardUserKey;
    private String cardKey;

    public PayEvent(PayRequest payRequest) {
        super();
        this.referenceId = payRequest.getReferenceId();
        this.phase = payRequest.getPhase();
        this.amount = payRequest.getAmount();
        this.currency = payRequest.getCurrency();
        this.installment = payRequest.getInstallment();
        this.cardUserKey = payRequest.getCardUserKey();
        this.cardKey = payRequest.getCardKey();
    }
}
