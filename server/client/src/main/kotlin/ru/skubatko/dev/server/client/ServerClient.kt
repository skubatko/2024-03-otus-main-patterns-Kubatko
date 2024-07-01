package ru.skubatko.dev.server.client

import org.springframework.web.client.RestTemplate

class ServerClient(
    private val restTemplate: RestTemplate
) {
    private val baseUrl = "/api/v1/game"
}
