package com.github.epay.service.statemachine.action.impl;

import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import com.github.epay.service.PaymentStateService;
import com.github.epay.service.statemachine.action.PaymentAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

/**
 * @author Raman Haurylau
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommitAction implements PaymentAction {

    private final PaymentStateService paymentStateService;

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        Long paymentId = getId(context.getMessage());

        log.info("Executing CommitAction for payment [{}]", paymentId);

        paymentStateService.paymentCommited(paymentId);
    }
}
