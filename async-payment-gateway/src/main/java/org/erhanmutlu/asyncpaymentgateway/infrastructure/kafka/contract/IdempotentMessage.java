package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class IdempotentMessage {

    private String trackingNumber;

    public IdempotentMessage() {
        this.trackingNumber = UUID.randomUUID().toString();
    }
}
