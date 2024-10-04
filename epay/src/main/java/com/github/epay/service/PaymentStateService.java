package com.github.epay.service;

import com.github.epay.domain.dto.common.PaymentDto;

/**
 * @author Raman Haurylau
 */
public interface PaymentStateService {
    PaymentDto paymentTransferred(Long paymentId);

    PaymentDto paymentCancelled(Long paymentId);

    PaymentDto paymentProcessed(Long paymentId);

    PaymentDto paymentCommited(Long paymentId);

    PaymentDto paymentPlaced(Long paymentId);
}
