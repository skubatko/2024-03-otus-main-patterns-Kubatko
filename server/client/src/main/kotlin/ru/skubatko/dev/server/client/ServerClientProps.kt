package ru.skubatko.dev.server.client

import ru.skubatko.dev.common.rest.RestProps
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("msa.clients.server")
class ServerClientProps(
    baseUrl: String = "http://localhost:8081",
    connectTimeout: Duration = Duration.ofSeconds(15),
    readTimeout: Duration = Duration.ofSeconds(40)
) : RestProps(baseUrl, connectTimeout, readTimeout)
