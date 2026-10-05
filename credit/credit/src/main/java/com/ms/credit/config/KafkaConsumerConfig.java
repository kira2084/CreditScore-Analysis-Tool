package com.ms.credit.config;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.ms.credit.dto.FinancialDataDTO;
import com.ms.credit.service.EmailService;
import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private EmailService emailService;  

    @Bean
    public ConsumerFactory<String, FinancialDataDTO> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Address of the Kafka server.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");  // Group ID for this consumer group.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());  // Deserializer for Kafka message keys.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());  // Deserializer for Kafka message values configured for FinancialDataDTO.
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(FinancialDataDTO.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FinancialDataDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FinancialDataDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());  // Set up the factory using the defined consumer factory.
        return factory;
    }


    @KafkaListener(topics = "credit-score-updates", groupId = "group_id")
    public void handleClaimStatusUpdate(FinancialDataDTO message) {
        emailService.sendEmail(message.getEmailId(), "Credit Score Update",
            "Your Credit Score has been updated to: " + message.getCreditScore());
    }
}
