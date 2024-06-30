package ru.skubatko.dev.server.client

import ru.skubatko.dev.api.models.auth.AuthReqDto
import ru.skubatko.dev.api.models.auth.AuthRespDto
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

class ServerClient(
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
