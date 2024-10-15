package com.github.api_gateway.service.exception

import org.springframework.http.HttpStatus

/**
 * @author Raman Haurylau
 */
enum class ExceptionEnum(val status: HttpStatus, val message: String) {
    // Endpoint
    ENDPOINT_EXCEPTION(HttpStatus.BAD_REQUEST, "An error occurred while processing request"),
    // Jwt
    AUTHENTICATION_TOKEN_EXCEPTION(HttpStatus.NOT_ACCEPTABLE, "Please verify the validity of your token/account"),
    // Auth
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "Authentication failed"),

    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "You have entered either the Username and/or Password incorrectly"),
}