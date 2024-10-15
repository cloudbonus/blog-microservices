package com.github.api_gateway.controller.advice

import com.github.api_gateway.service.exception.ExceptionEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Raman Haurylau
 */
@RestControllerAdvice
class BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val violations: MutableList<Violation> = mutableListOf()

        e.bindingResult.allErrors.forEach {
            if (it is FieldError) {
                violations.add(Violation(it.field, it.defaultMessage))
            } else {
                violations.add(Violation(it.objectName, it.defaultMessage))
            }
        }

        return ResponseEntity
            .status(e.statusCode)
            .body(ValidationErrorResponse("VALIDATION_FAILED", violations, System.currentTimeMillis()))
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleCustomEntityException(): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(HttpStatus.UNAUTHORIZED.name, ExceptionEnum.AUTHENTICATION_FAILED.message, System.currentTimeMillis()))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.name, e.message, System.currentTimeMillis()))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name, e.message, System.currentTimeMillis()))
    }
}