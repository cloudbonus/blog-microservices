package com.github.api_gateway.controller

import com.github.api_gateway.controller.dto.request.UserRequest
import com.github.api_gateway.controller.dto.response.TokenResponse
import com.github.api_gateway.controller.util.marker.UserValidationGroup
import com.github.api_gateway.service.AuthService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


/**
 * @author Raman Haurylau
 */
@Validated
@RestController
@RequestMapping("auth")
class AuthController(val authService: AuthService) {

    @GetMapping("login")
    fun login(@RequestBody @Validated(UserValidationGroup.onAuthenticate::class) request: UserRequest): Flux<TokenResponse> {
        return Flux.just(authService.login(request))
    }
}