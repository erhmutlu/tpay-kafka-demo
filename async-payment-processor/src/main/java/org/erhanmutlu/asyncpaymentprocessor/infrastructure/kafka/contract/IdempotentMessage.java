package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentPreAuthMessage.class, name = "PRE_AUTH"),
        @JsonSubTypes.Type(value = PaymentAuthMessage.class, name = "AUTH"),
})
public abstract class IdempotentMessage {

    private String type;
    @Getter
    private String trackingNumber;
}
