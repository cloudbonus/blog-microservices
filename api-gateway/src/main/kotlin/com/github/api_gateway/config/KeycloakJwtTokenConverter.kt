package com.github.api_gateway.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono


/**
 * @author Raman Haurylau
 */
class KeycloakJwtTokenConverter(
    private val properties: JwtAuthConverterProperties,
) : Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    companion object{
        const val RESOURCE_ACCESS = "resource_access"
        const val ROLES = "roles"
        const val ROLE_PREFIX = "ROLE_"
    }

    override fun convert(jwt: Jwt): Mono<AbstractAuthenticationToken> {

        val claims: Map<String, Any> = jwt.getClaimAsMap(RESOURCE_ACCESS)

        val resources: Map<String, Any> = claims[properties.resourceId] as Map<String, Any>

        val roles: List<String> =  resources[ROLES] as List<String>

        val authorities: List<SimpleGrantedAuthority> = roles.map { role ->
            SimpleGrantedAuthority(ROLE_PREFIX + role)
        }.distinct()

        val principalClaimName: String = properties
            .getPrincipalAttribute()
            .map(jwt::getClaimAsString)
            .orElse(jwt.getClaimAsString(JwtClaimNames.SUB))

        return Mono.just(JwtAuthenticationToken(jwt, authorities, principalClaimName))

    }
}