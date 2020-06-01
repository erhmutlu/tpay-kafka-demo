package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class MessageProducerInterceptor implements ProducerInterceptor {

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        log.debug("onSend topic={}, partition: {}, key={} value={}", record.topic(), record.partition(), record.key(), record.value());
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception e) {
        log.debug("onAck topic={}, partition: {}, offset={}", metadata.topic(), metadata.partition(), metadata.offset());
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
