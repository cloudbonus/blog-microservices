package com.github.api_gateway.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono


/**
 * @author Raman Haurylau
 */
class KeycloakJwtTokenConverter(
    private val properties: JwtAuthProperties,
) : Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    companion object {
        const val REALM_ACCESS: String = "realm_access"
        const val ROLES: String = "roles"
    }

    override fun convert(jwt: Jwt): Mono<AbstractAuthenticationToken> {

        val realmAccess: Map<String, Collection<String>>? = jwt.getClaim(REALM_ACCESS)
        val grantedAuthorities: MutableList<GrantedAuthority> = mutableListOf()

        if (!realmAccess.isNullOrEmpty()) {
            val realmRoles: Collection<String>? = realmAccess[ROLES]
            if (!realmRoles.isNullOrEmpty()) {
                realmRoles.forEach {
                    grantedAuthorities.add(SimpleGrantedAuthority(it))
                }
            }
        }

        val principalClaimName: String = properties
            .getPrincipalAttribute()
            .map(jwt::getClaimAsString)
            .orElse(jwt.getClaimAsString(JwtClaimNames.SUB))

        return Mono.just(JwtAuthenticationToken(jwt, grantedAuthorities, principalClaimName))
    }
}