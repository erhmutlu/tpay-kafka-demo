package org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.contract.IdempotentMessage;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.interceptor.MessageProducerInterceptor;
import org.erhanmutlu.asyncpaymentgateway.infrastructure.kafka.interceptor.MessageProducerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean("kafkaTransactionManager")
    public KafkaTransactionManager kafkaTransactionManager() {
        KafkaTransactionManager ktm = new KafkaTransactionManager(producerFactory());
        ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_NEVER);
        ktm.setRollbackOnCommitFailure(true);
        return ktm;
    }

    @Bean
    public ProducerFactory<String, IdempotentMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ProducerListener<String, IdempotentMessage> producerListener() {
        return new MessageProducerListener();
    }

    @Bean
    public KafkaTemplate<String, IdempotentMessage> kafkaTemplate() {
        KafkaTemplate<String, IdempotentMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setProducerListener(producerListener());
        return kafkaTemplate;
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "payment.publish.");  //makes producer is idempotent
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MessageProducerInterceptor.class.getName());
        props.put(ProducerConfig.RETRIES_CONFIG, 10);  //default 3
//        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 16 * 1024 * 1024);  //default 32 MB
        return props;
    }
}