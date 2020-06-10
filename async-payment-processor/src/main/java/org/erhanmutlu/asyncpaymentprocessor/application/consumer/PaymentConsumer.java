package org.erhanmutlu.asyncpaymentprocessor.application.consumer;

import lombok.extern.slf4j.Slf4j;
import org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract.PaymentAuthMessage;
import org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract.PaymentPostAuthMessage;
import org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract.PaymentPreAuthMessage;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "t_payment_request", groupId = "payment-processor-group", containerFactory = "kafkaListenerContainerFactory")
public class PaymentConsumer {

    @KafkaHandler
    public void consumePaymentAuthMessage(PaymentAuthMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("consuming auth message from partition: {} with trackingNumber: {}, referenceId: {}", partition, message.getTrackingNumber(), message.getReferenceId());
        wait3();
        log.info("done");
    }

    @KafkaHandler
    public void consumePaymentPreAuthMessage(PaymentPreAuthMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("consuming pre-auth message from partition: {} with trackingNumber: {}, referenceId: {}", partition, message.getTrackingNumber(), message.getReferenceId());
        wait3();
        log.info("done");
    }

    @KafkaHandler
    public void consumePaymentPostAuthMessage(PaymentPostAuthMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("consuming post-auth message from partition: {} with trackingNumber: {}", partition, message.getTrackingNumber());
        wait3();
        log.info("done");
    }

    private void wait3() {
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
