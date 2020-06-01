package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class MessageProducerListener implements ProducerListener<String, IdempotentMessage> {

    @Override
    public void onSuccess(ProducerRecord<String, IdempotentMessage> producerRecord, RecordMetadata recordMetadata) {
        log.info("success for writing topic: {} for uniqueId: {}", producerRecord.topic(), producerRecord.value().getTrackingNumber());
    }

    @Override
    public void onError(ProducerRecord<String, IdempotentMessage> producerRecord, Exception exception) {
        log.info("failure for writing topic: {} for uniqueId: {}", producerRecord.topic(), producerRecord.value().getTrackingNumber());
    }
}
