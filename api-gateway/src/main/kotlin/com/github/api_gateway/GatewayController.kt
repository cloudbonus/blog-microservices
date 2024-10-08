package com.github.api_gateway

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


/**
 * @author Raman Haurylau
 */
@RestController
class GatewayController {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    fun index(authentication: Authentication): Mono<ResponseEntity<String>> {
        log.info("{}", authentication.isAuthenticated)
        return Mono.just(ResponseEntity.ok("Hello ${authentication.name}. Roles: ${authentication.authorities}"))
    }
}