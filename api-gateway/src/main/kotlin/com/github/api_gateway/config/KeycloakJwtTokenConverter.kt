package com.github.api_gateway.config

import org.springframework.core.convert.converter.Converter
import org.springframework.lang.NonNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import java.util.*

/**
 * @author Raman Haurylau
 */
class KeycloakJwtTokenConverter(
    val jwtGrantedAuthoritiesConverter: JwtGrantedAuthoritiesConverter,
    val properties: TokenConverterProperties,
) : Converter<Jwt, JwtAuthenticationToken> {

    companion object{
        const val RESOURCE_ACCESS = "resource_access"
        const val ROLES = "roles"
        const val ROLE_PREFIX = "ROLE_"
    }

    override fun convert(jwt: Jwt): JwtAuthenticationToken? {

        var roles = Optional.of(jwt).map {
            token -> token.getClaimAsMap(RESOURCE_ACCESS)
        }.map {claimMap ->
            claimMap.get(properties.resourceId) as Map<String, Any>
        }.map { resourceData ->
            resourceData.get(ROLES)
        }
        roles.map { role ->
            SimpleGrantedAuthority(ROLE_PREFIX + role)
        }
        TODO("Not yet implemented")
    }


}