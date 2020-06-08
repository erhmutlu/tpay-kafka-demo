package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class IdempotentMessage {

    private final String type;
    private final String trackingNumber;

    public IdempotentMessage(String type) {
        this.type = type;
        this.trackingNumber = UUID.randomUUID().toString();
    }
}
