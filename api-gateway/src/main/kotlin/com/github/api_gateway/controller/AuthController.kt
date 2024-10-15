package com.github.api_gateway.controller

import com.github.api_gateway.controller.dto.request.UserRequest
import com.github.api_gateway.controller.util.marker.UserValidationGroup
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


/**
 * @author Raman Haurylau
 */
@Validated
@RestController
@RequestMapping("auth")
class AuthController {

    @GetMapping("login")
    fun login(@RequestBody @Validated(UserValidationGroup.onAuthenticate::class) request: UserRequest) {
        return Mono.just(Unit::class)
    //return Mono.just(ResponseEntity.ok("Hello ${authentication.name}. Roles: ${authentication.authorities}"))
    }
}