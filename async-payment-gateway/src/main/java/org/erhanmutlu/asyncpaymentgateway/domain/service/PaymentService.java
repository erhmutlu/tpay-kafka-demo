package org.erhanmutlu.asyncpaymentgateway.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request.PayRequest;
import org.erhanmutlu.asyncpaymentgateway.domain.PayEvent;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.port.KafkaAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    @Value("${kafka.topics.payment:t_payment}")
    public String paymentRequestTopic;

    private final KafkaAdapter kafkaAdapter;

    public PaymentService(KafkaAdapter kafkaAdapter) {
        this.kafkaAdapter = kafkaAdapter;
    }

    public String pay(PayRequest payRequest) {
        log.info("PayRequest retrieved with referenceId: {}, amount: {}, currency: {}", payRequest.getReferenceId(), payRequest.getAmount(), payRequest.getCurrency());
        PayEvent payEvent = new PayEvent(payRequest);
        kafkaAdapter.publish(paymentRequestTopic, payEvent);
        return payEvent.getTrackingNumber();
    }
}
