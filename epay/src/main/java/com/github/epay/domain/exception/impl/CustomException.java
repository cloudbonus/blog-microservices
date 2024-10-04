package com.github.epay.domain.exception.impl;

import com.github.epay.domain.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Raman Haurylau
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;
}
