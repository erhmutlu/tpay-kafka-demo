package org.erhanmutlu.paymentrequestlogger.application.consumer;

import lombok.extern.slf4j.Slf4j;
import org.erhanmutlu.paymentrequestlogger.infrastructure.kafka.contract.PaymentRequestMessage;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "t_payment_request", groupId = "payment-request-logger-group", containerFactory = "kafkaListenerContainerFactory")
public class PaymentConsumer {

    @KafkaHandler
    public void consumePaymentAuthMessage(PaymentRequestMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("[RequestLogger]payment request is consumed from partition: {} with trackingNumber: {}, referenceId: {}", partition, message.getTrackingNumber(), message.getReferenceId());
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
