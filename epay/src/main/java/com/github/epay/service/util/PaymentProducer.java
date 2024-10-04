package com.github.epay.service.util;

import com.github.epay.config.kafka.KafkaTopicProperties;
import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.enums.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Raman Haurylau
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentDto> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    public PaymentDto sendPayment(PaymentDto paymentDto) {
        kafkaTemplate.send(kafkaTopicProperties.getTopic(KafkaTopic.UPDATE), paymentDto);
        log.info("Payment request produced {}", paymentDto);
        return paymentDto;
    }
}
