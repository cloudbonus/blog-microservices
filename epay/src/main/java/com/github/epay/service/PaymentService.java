package com.github.epay.service;

import com.github.epay.domain.dto.request.PaymentRequest;

/**
 * @author Raman Haurylau
 */
public interface PaymentService {
    void cancel(PaymentRequest request);

    void process(PaymentRequest request);
}
