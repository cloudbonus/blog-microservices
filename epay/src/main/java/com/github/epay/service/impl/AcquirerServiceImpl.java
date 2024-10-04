package com.github.epay.service.impl;

import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.dto.request.PaymentRequest;
import com.github.epay.domain.enums.PaymentState;
import com.github.epay.domain.exception.ExceptionEnum;
import com.github.epay.domain.exception.impl.CustomException;
import com.github.epay.domain.mapper.PaymentMapper;
import com.github.epay.repository.PaymentRepository;
import com.github.epay.repository.entity.Payment;
import com.github.epay.service.AcquirerService;
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
public class AcquirerServiceImpl implements AcquirerService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


    @Override
    public PaymentDto cancel(PaymentRequest request) {

        Payment payment = paymentRepository
                .findByPaymentId(request.paymentId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.ORDER_NOT_FOUND));

        log.info("Starting cancel of payment [{}]", request.paymentId());
        return paymentMapper.toPaymentDto(payment);
    }

    @Override
    public PaymentDto process(PaymentRequest request) {
        log.info("Starting creation of new payment");
        Payment payment = paymentMapper.toPayment(request);

        payment.setState(PaymentState.NEW);
        payment = paymentRepository.save(payment);

        return paymentMapper.toPaymentDto(payment);
    }
}
