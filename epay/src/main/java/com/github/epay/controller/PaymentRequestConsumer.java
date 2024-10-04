package com.github.epay.controller;

import com.github.epay.domain.dto.request.PaymentRequest;
import com.github.epay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentRequestConsumer {

    private static final String CANCEL_TOPIC = "${topic.names.cancel}";
    private static final String PROCESS_TOPIC = "${topic.names.process}";

    private final PaymentService paymentService;

    @KafkaListener(topics = CANCEL_TOPIC)
    public void consumePaymentRequestOnCancel(PaymentRequest paymentRequest) {
        log.info("Payment request {}", paymentRequest);
        paymentService.cancel(paymentRequest);
    }

    @KafkaListener(topics = PROCESS_TOPIC)
    public void consumePaymentRequestOnProcess(PaymentRequest paymentRequest) {
        log.info("Payment request {}", paymentRequest);
        paymentService.process(paymentRequest);
    }
}