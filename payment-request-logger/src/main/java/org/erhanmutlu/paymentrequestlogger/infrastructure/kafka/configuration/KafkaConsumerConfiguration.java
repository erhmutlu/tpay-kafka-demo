package org.erhanmutlu.paymentrequestlogger.infrastructure.kafka.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.erhanmutlu.paymentrequestlogger.infrastructure.kafka.contract.PaymentRequestMessage;
import org.erhanmutlu.paymentrequestlogger.infrastructure.kafka.interceptor.CustomErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.LogIfLevelEnabled;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ThreadPoolTaskExecutor messageProcessorExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(5);
        exec.setMaxPoolSize(10);
        exec.setKeepAliveSeconds(60);
        exec.setThreadNamePrefix("kafkaConsumer-");
        return exec;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PaymentRequestMessage>> kafkaListenerContainerFactory(KafkaTemplate kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, PaymentRequestMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(5);
        factory.setAutoStartup(true);
        factory.setReplyTemplate(kafkaTemplate);
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setConsumerTaskExecutor(messageProcessorExecutor());
        containerProperties.setAckMode(ContainerProperties.AckMode.RECORD);
        containerProperties.setIdleEventInterval(60000L);
        containerProperties.setCommitLogLevel(LogIfLevelEnabled.Level.INFO);

        return factory;
    }

    @Bean
    public ConsumerAwareListenerErrorHandler kafkaListenerErrorHandler() {
        return new CustomErrorHandler();
    }

    @Bean
    public ConsumerFactory<String, PaymentRequestMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(PaymentRequestMessage.class, new ObjectMapper(), false));
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "4000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "50000");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
        return props;
    }


}
