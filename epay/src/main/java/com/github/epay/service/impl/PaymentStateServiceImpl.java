package com.github.epay.service.impl;

import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.enums.PaymentState;
import com.github.epay.domain.exception.ExceptionEnum;
import com.github.epay.domain.exception.impl.CustomException;
import com.github.epay.domain.mapper.PaymentMapper;
import com.github.epay.repository.PaymentRepository;
import com.github.epay.repository.entity.Payment;
import com.github.epay.service.PaymentStateService;
import com.github.epay.service.util.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raman Haurylau
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentStateServiceImpl implements PaymentStateService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @Override
    public PaymentDto paymentCommited(Long id) {
        PaymentDto paymentDto = updatePaymentStateAndReturnDto(id, PaymentState.COMMITED);

        return paymentProducer.sendPayment(paymentDto);
    }

    @Override
    public PaymentDto paymentProcessed(Long id) {
        return updatePaymentStateAndReturnDto(id, PaymentState.PROCESSED);
    }

    @Override
    public PaymentDto paymentPlaced(Long id) {
       return updatePaymentStateAndReturnDto(id, PaymentState.PLACED);
    }

    @Override
    public PaymentDto paymentTransferred(Long id) {
        return updatePaymentStateAndReturnDto(id, PaymentState.TRANSFERRED);
    }

    @Override
    public PaymentDto paymentCancelled(Long id) {
        return updatePaymentStateAndReturnDto(id, PaymentState.CANCELED);
    }

    private PaymentDto updatePaymentStateAndReturnDto(Long id, PaymentState paymentState) {
        log.info("Updating payment [{}] to state [{}]", id, paymentState);

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.ORDER_NOT_FOUND));

        payment.setState(paymentState);

        return paymentMapper.toPaymentDto(payment);
    }
}
