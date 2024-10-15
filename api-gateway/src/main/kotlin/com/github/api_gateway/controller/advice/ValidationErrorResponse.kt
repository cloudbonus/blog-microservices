package com.github.api_gateway.controller.advice

/**
 * @author Raman Haurylau
 */
data class ValidationErrorResponse(val status: String, val violations: List<Violation>, val timestamp: Long)