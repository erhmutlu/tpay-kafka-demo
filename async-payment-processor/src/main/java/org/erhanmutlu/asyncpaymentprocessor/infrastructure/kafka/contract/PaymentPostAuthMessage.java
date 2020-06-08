package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentPostAuthMessage extends IdempotentMessage {

    private PaymentPhase phase = PaymentPhase.PRE_AUTH;
    private String referenceId;
    private BigDecimal amount;
    private String currency;
}
