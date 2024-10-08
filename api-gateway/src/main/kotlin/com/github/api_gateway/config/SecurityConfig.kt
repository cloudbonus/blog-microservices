package com.github.api_gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


/**
 * @author Raman Haurylau
 */
@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity, properties: JwtAuthConverterProperties): SecurityWebFilterChain {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange { exchange ->
                exchange.pathMatchers("/eureka/**")
                    .permitAll()
                    .anyExchange()
                    .authenticated()
           }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt {jwtCustomizer ->
                jwtCustomizer.jwtAuthenticationConverter(KeycloakJwtTokenConverter(properties))
            }}
        return http.build()
    }

}