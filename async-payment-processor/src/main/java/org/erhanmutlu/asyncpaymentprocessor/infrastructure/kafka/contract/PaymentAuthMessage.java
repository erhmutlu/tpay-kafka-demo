package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAuthMessage extends PaymentMessage {

    private PaymentPhase phase = PaymentPhase.AUTH;
}
