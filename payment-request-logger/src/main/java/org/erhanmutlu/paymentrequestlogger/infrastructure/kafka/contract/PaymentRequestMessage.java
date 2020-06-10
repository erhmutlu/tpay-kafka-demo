package org.erhanmutlu.paymentrequestlogger.infrastructure.kafka.contract;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestMessage {

    private String type;
    private String trackingNumber;
    private String phase;
    private String referenceId;
    private BigDecimal amount;
    private String currency;
    private Integer installment;
    private String cardUserKey;
    private String cardKey;
}
