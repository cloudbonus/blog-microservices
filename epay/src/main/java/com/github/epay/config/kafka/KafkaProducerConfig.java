package com.github.epay.config.kafka;

import com.github.epay.domain.dto.common.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * @author Raman Haurylau
 */
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, PaymentDto> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties(null);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, PaymentDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
