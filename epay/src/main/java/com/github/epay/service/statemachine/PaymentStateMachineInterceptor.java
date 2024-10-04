package com.github.epay.service.statemachine;

import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import static com.github.epay.service.statemachine.PaymentStateMachine.PAYMENT_ID;
import static java.util.Objects.isNull;

/**
 * @author Raman Haurylau
 */
@Slf4j
@Builder
@Component
public class PaymentStateMachineInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        if (isNull(message) || isNull(message.getHeaders().get(PAYMENT_ID))) {
            log.warn("Invalid state transition, message will be ignored: {}",
                    isNull(message) ? "Message is null" : PAYMENT_ID + " is null");
            return;
        }

        log.info("PreStateChange intercepted - state from [{}], state to [{}], event [{}]",
                stateMachine.getState().getId(), state.getId(), message.getPayload());
    }
}