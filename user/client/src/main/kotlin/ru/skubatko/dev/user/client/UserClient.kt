package ru.skubatko.dev.user.client

import ru.skubatko.dev.api.models.user.UserDto
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class UserClient(
    private val restTemplate: RestTemplate
) {
    private val baseUrl = "/api/v1/user"

    fun findByLogin(login: String): UserDto? =
        restTemplate.getForObject(
            UriComponentsBuilder.fromPath(baseUrl).queryParam("login", login).encode().toUriString(),
            UserDto::class.java
        )
}
