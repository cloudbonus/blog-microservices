package com.github.api_gateway.controller.dto.request

import com.github.api_gateway.controller.util.marker.BaseMarker
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

/**
 * @author Raman Haurylau
 */
data class UserRequest(
    @field:Pattern(
        message = "Invalid username",
        regexp = "^[A-Za-z][A-Za-z0-9._-]{3,20}$"
    )
    @field:NotBlank(
        message = "Username is mandatory",
        groups = [BaseMarker.First::class]
    )
    val username: String,
    @field:NotBlank(
        message = "Password is mandatory",
        groups = [BaseMarker.First::class]
    )
    val password: String,
    @field:Pattern(
        message = "Invalid  email",
        regexp = "^[A-Za-z][a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @field:NotBlank(
        message = "Email cannot be empty",
        groups = [BaseMarker.Second::class])
    val email: String,
)
