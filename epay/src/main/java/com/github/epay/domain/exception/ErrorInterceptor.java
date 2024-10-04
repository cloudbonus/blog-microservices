package com.github.epay.domain.exception;

import com.github.epay.domain.exception.impl.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Raman Haurylau
 */
@Slf4j
@Aspect
public class ErrorInterceptor{

    @AfterThrowing(pointcut = "execution(* com.github.epay..* (..))", throwing = "ex")
    public void errorInterceptor(Exception ex) {
        if (ex instanceof CustomException customException) {
            log.error("Custom exception {} occurred: {}", customException.getExceptionEnum().getStatus(), customException.getExceptionEnum().getMessage());
        } else if (ex instanceof RuntimeException runtimeException) {
            log.error("RuntimeException occurred: {}", runtimeException.getMessage());
        } else {
            log.error("Exception occurred: {}", ex.getMessage());
        }
    }
}
