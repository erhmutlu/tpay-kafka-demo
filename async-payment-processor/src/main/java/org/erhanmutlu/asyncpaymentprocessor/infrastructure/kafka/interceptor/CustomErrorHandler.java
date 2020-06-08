package org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.erhanmutlu.asyncpaymentprocessor.infrastructure.kafka.contract.IdempotentMessage;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@Slf4j
public class CustomErrorHandler implements ConsumerAwareListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        IdempotentMessage payload = (IdempotentMessage) message.getPayload();
        log.error("error occurred: {}", payload.getTrackingNumber(), exception);

        MessageHeaders headers = message.getHeaders();
        consumer.seek(new TopicPartition(
                        headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                        headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
                headers.get(KafkaHeaders.OFFSET, Long.class));
        return null;
    }
}
