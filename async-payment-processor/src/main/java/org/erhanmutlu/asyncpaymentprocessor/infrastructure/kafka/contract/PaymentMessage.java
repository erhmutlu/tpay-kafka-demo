package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class PaymentMessage extends IdempotentMessage {

    private PaymentPhase phase;
    private String referenceId;
    private BigDecimal amount;
    private String currency;
    private Integer installment;
    private String cardUserKey;
    private String cardKey;
}
