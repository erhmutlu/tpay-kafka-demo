package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentPreAuthMessage extends PaymentMessage {

    private PaymentPhase phase = PaymentPhase.PRE_AUTH;
}
