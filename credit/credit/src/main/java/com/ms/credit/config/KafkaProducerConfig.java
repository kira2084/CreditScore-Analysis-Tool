package com.ms.credit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.ms.credit.dto.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {
    

    @Bean
    public ProducerFactory<String, NotificationDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Kafka server address.
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Serializer for message keys.
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);  // Serializer for message values.
        return new DefaultKafkaProducerFactory<>(configProps);  // Return the producer factory configured with these settings.
    }


    @Bean
    public KafkaTemplate<String, NotificationDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());  // Create and return a KafkaTemplate based on the producer factory.
    }
}
