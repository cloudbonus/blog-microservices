package com.github.epay.service.statemachine;

import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

/**
 * @author Raman Haurylau
 */
@RequiredArgsConstructor
public class PaymentStateMachine {

    public static final String PAYMENT_ID = "paymentId";

    private final StateMachine<PaymentState, PaymentEvent> sm;
    private final Long id;

    public PaymentState sendEvent(PaymentEvent paymentEvent) {

        sm.sendEvent(MessageBuilder
                .withPayload(paymentEvent)
                .setHeader(PAYMENT_ID, id)
                .build());

        return sm.getState().getId();
    }
}
