package ru.skubatko.dev.user.client

import ru.skubatko.dev.common.rest.RestProps
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("msa.clients.user")
class UserClientProps(
    baseUrl: String = "http://localhost:8183",
    connectTimeout: Duration = Duration.ofSeconds(15),
    readTimeout: Duration = Duration.ofSeconds(40)
) : RestProps(baseUrl, connectTimeout, readTimeout)
