package com.github.api_gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.Optional

/**
 * @author Raman Haurylau
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
class JwtAuthConverterProperties(
    var resourceId: String?,
    var principalAttribute: String?,
) {
    fun getPrincipalAttribute(): Optional<String> {
        return Optional.ofNullable(principalAttribute)
    }
}