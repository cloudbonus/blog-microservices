package com.github.epay.service;

import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.dto.request.PaymentRequest;

/**
 * @author Raman Haurylau
 */
public interface AcquirerService {
    PaymentDto cancel(PaymentRequest request);

    PaymentDto process(PaymentRequest request);
}
