package com.github.epay.domain.mapper;


import com.github.epay.domain.dto.common.PaymentDto;
import com.github.epay.domain.dto.request.PaymentRequest;
import com.github.epay.domain.dto.response.PaymentResponse;
import com.github.epay.repository.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    Payment toPayment(PaymentRequest request);

    PaymentResponse toPaymentResponse(PaymentDto paymentDto);

    PaymentDto toPaymentDto(Payment payment);
}