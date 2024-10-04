package com.github.epay.service.statemachine;

import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

/**
 * @author Raman Haurylau
 */
@Component
@RequiredArgsConstructor
public class PaymentStateMachineFactory {

    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    private final PaymentStateMachineInterceptor paymentStateMachineInterceptor;

    public PaymentStateMachine create(PaymentDto paymentDto) {
        StateMachine<PaymentState, PaymentEvent> sm =
                stateMachineFactory.getStateMachine(String.valueOf(paymentDto.getId()));

        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(paymentStateMachineInterceptor);
            sma.resetStateMachine(new DefaultStateMachineContext<>(paymentDto.getState(), null,
                    null, null));
        });

        sm.start();

        return new PaymentStateMachine(sm, paymentDto.getId());
    }

}
