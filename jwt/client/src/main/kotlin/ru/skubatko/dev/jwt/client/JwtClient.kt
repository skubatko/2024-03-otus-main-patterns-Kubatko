package ru.skubatko.dev.jwt.client

import ru.skubatko.dev.api.models.jwt.JwtGenerationReqDto
import ru.skubatko.dev.api.models.jwt.JwtGenerationRespDto
import ru.skubatko.dev.api.models.jwt.JwtUsernameRespDto
import ru.skubatko.dev.api.models.jwt.JwtValidationReqDto
import ru.skubatko.dev.api.models.jwt.JwtValidationRespDto
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class JwtClient(
    private val restTemplate: RestTemplate
) {
    private val baseUrl = "/api/v1/jwt"

    fun generate(jwtGenerationReqDto: JwtGenerationReqDto) =
        restTemplate.postForObject(
            "$baseUrl/generate",
            HttpEntity(jwtGenerationReqDto),
            JwtGenerationRespDto::class.java
        )

    fun getUsername(jwtToken: String): JwtUsernameRespDto? =
        restTemplate.getForObject(
            UriComponentsBuilder.fromPath(baseUrl).queryParam("token", jwtToken).encode().toUriString(),
            JwtUsernameRespDto::class.java
        )

    fun isTokenValid(jwtValidationReqDto: JwtValidationReqDto) =
        restTemplate.postForObject(
            "$baseUrl/generate",
            HttpEntity(jwtValidationReqDto),
            JwtValidationRespDto::class.java
        )
}
