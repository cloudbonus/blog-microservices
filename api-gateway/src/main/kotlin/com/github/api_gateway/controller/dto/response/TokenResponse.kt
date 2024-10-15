package com.github.api_gateway.controller.dto.response

/**
 * @author Raman Haurylau
 */
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshExpiresIn: Int,
    val tokenType: String,
    val sessionState: String,
    val scope: String,
)
