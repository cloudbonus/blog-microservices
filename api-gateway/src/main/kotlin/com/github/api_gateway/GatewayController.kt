package com.github.api_gateway

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


/**
 * @author Raman Haurylau
 */
@RestController
class GatewayController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/whoami")
    fun index(): Mono<String> {
        return Mono.just("Hello")
    }
}