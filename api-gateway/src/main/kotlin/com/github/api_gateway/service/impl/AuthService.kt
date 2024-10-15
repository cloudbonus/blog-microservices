package com.github.api_gateway.service.impl

import com.github.api_gateway.config.JwtAuthProperties
import com.github.api_gateway.controller.dto.request.UserRequest
import com.github.api_gateway.controller.dto.response.TokenResponse
import com.github.api_gateway.service.AuthService
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
        const val ACCESS_TOKEN: String = "Access-Token"
        const val REFRESH_TOKEN: String = "Refresh-Token"
        const val EXPIRES_IN: String = "Expires-In"
        const val DEVICE_ID: String = "Device-Id"
    }

    fun login(
        request: UserRequest,
        serverRequest: ServerHttpRequest,
        serverResponse: ServerHttpResponse,
    ): ResponseEntity<Any> {
        log.info("Start to get access token")

        //val deviceId: String = serverRequest.headers.de

        val token: TokenResponse? = getAccessToken(request)

        if (token == null) {
            throw
        }

        return ResponseEntity.ok().body<T>(
            BaseResponseDto.builder()
                .status("SUCCESS")
                .build()
        )
    }

    fun refreshToken(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse): ResponseEntity<Any> {
        log.info("Start to refresh access token")

        val deviceId: String = servletRequest.getHeader(DEVICE_ID)

        val tokenDto: TokenDto? = this.getRefreshToken(sessionStorage.getCache(REFRESH_TOKEN, deviceId))

        servletResponse.addHeader(ACCESS_TOKEN, tokenDto.getAccessToken())
        servletResponse.addHeader(EXPIRES_IN, java.lang.String.valueOf(tokenDto.getExpiresIn()))

        sessionStorage.putCache(REFRESH_TOKEN, deviceId, tokenDto.getRefreshToken(), tokenDto.getRefreshExpiresIn())

        return ResponseEntity.ok().body<T>(
            BaseResponseDto.builder()
                .status("SUCCESS")
                .build()
        )
    }

    private fun getAccessToken(request: UserRequest): TokenResponse? {
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

        return response
    }

    private fun getRefreshToken(refreshToken: String): TokenResponse? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "refresh_token")
        requestBody.add("refresh_token", refreshToken)
        requestBody.add("client_id", properties.resourceId)
        requestBody.add("client_secret", properties.secret)

        val response: TokenResponse? = restTemplate.postForObject(
            properties.tokenUrl,
            HttpEntity(requestBody, headers),
            TokenResponse::class.java
        )

        return response
    }
}