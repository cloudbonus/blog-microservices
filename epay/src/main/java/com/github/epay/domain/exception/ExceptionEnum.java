package com.github.epay.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Raman Haurylau
 */
@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {
    // Endpoint
    ENDPOINT_EXCEPTION(HttpStatus.BAD_REQUEST, "An error occurred while processing request"),
    // State Machine
    INVALID_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, "Header required"),
    STATE_TRANSITION_EXCEPTION(HttpStatus.BAD_REQUEST, "Unable to change state"),
    //  Payment
    PAYMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "The payment has not been received"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment not found"),
    ORDERS_NOT_FOUND(HttpStatus.NOT_FOUND, "No orders could be found");

    private final HttpStatus status;
    private final String message;
}
