package com.github.epay.domain.dto.common;

import com.github.epay.domain.enums.PaymentState;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Raman Haurylau
 */
@Getter
@Setter
public class PaymentDto {
    private Long id;
    private String fullName;
    private PaymentState state;
    private Long paymentId;
}
