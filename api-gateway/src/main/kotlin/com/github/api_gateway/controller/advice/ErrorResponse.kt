package com.github.api_gateway.controller.advice

/**
 * @author Raman Haurylau
 */
data class ErrorResponse(val status: String, val message: String?, val timestamp: Long)
