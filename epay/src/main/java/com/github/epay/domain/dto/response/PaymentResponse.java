package com.github.epay.domain.dto.response;

import com.github.epay.domain.enums.PaymentState;

/**
 * @author Raman Haurylau
 */
public record PaymentResponse(Long paymentId, String fullName, PaymentState state) {
}
