package com.github.api_gateway.service.impl

import com.github.api_gateway.config.JwtAuthProperties
import com.github.api_gateway.controller.dto.request.UserRequest
import com.github.api_gateway.controller.dto.response.TokenResponse
import com.github.api_gateway.service.AuthService
import com.github.api_gateway.service.exception.ExceptionEnum
import com.github.api_gateway.service.exception.impl.CustomException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


/**
 * @author Raman Haurylau
 */
@Service
class AuthService(
    private val properties: JwtAuthProperties,
    private val restTemplate: RestTemplate,
) : AuthService {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    companion object {
        const val GRANT_TYPE_PASSWORD: String = "password"
    }

    override fun login(
        request: UserRequest
    ): TokenResponse {
        log.info("Start to get access token")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()

        requestBody.add("grant_type", GRANT_TYPE_PASSWORD)
        requestBody.add("client_id", properties.resourceId)
        requestBody.add("client_secret", properties.secret)
        requestBody.add("username", request.username)
        requestBody.add("password", request.password)

        val response: TokenResponse? = restTemplate.postForObject(
            properties.tokenUrl,
            HttpEntity(requestBody, headers),
            TokenResponse::class.java
        )

        if (response == null) {
            throw CustomException(ExceptionEnum.AUTHENTICATION_FAILED)
        }

        return response
    }
}