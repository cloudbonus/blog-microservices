package com.github.epay.service.statemachine.action;

import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import com.github.epay.domain.exception.ExceptionEnum;
import com.github.epay.domain.exception.impl.CustomException;
import org.springframework.messaging.Message;
import org.springframework.statemachine.action.Action;

import static com.github.epay.service.statemachine.PaymentStateMachine.PAYMENT_ID;
import static java.util.Objects.nonNull;

/**
 * @author Raman Haurylau
 */
public interface PaymentAction extends Action<PaymentState, PaymentEvent> {

    default Long getId(Message<PaymentEvent> message) {
        Object obj = message.getHeaders().get(PAYMENT_ID);
        if (nonNull(obj)) {
            return Long.valueOf(obj.toString());
        } else {
            throw new CustomException(ExceptionEnum.INVALID_MESSAGE);
        }
    }
}
