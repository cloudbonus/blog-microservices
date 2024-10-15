package com.github.api_gateway.service

import com.github.api_gateway.controller.dto.request.UserRequest
import com.github.api_gateway.controller.dto.response.TokenResponse
import com.nimbusds.oauth2.sdk.TokenRequest

/**
 * @author Raman Haurylau
 */
interface AuthService {
    fun login(request: UserRequest): TokenResponse
}