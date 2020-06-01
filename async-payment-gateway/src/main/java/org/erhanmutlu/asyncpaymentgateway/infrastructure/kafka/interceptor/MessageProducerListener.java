package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class MessageProducerListener implements ProducerListener<String, IdempotentMessage> {

    @Override
    public void onSuccess(String topic, Integer partition, String key, IdempotentMessage message, RecordMetadata recordMetadata) {
        log.info("success for writing topic: {} for uniqueId: {}", topic, message.getTrackingNumber());
    }

    @Override
    public void onError(String topic, Integer partition, String key, IdempotentMessage message, Exception exception) {
        log.info("failure for writing topic: {} for uniqueId: {}", topic, message.getTrackingNumber());
    }
}
