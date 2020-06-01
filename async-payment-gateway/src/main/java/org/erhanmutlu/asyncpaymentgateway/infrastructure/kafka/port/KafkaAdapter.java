package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.port;

import lombok.extern.slf4j.Slf4j;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KafkaAdapter {

    private KafkaTemplate<String, IdempotentMessage> kafkaTemplate;

    public KafkaAdapter(KafkaTemplate<String, IdempotentMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void publish(String topicName, IdempotentMessage message) {
        log.info("message with trackingNumber: {} will be written into topic: {}", message.getTrackingNumber(), topicName);
        kafkaTemplate.send(topicName, message.getTrackingNumber(), message);
    }
}
