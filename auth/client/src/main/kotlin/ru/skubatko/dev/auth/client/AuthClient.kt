package ru.skubatko.dev.auth.client

import ru.skubatko.dev.api.models.auth.AuthReqDto
import ru.skubatko.dev.api.models.auth.AuthRespDto
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

class AuthClient(
    private val restTemplate: RestTemplate
) {
    private val baseUrl = "/api/v1/auth"

    fun login(authReqDto: AuthReqDto) =
        restTemplate.postForObject(
            "$baseUrl/login",
            HttpEntity(authReqDto),
            AuthRespDto::class.java
        )
}
