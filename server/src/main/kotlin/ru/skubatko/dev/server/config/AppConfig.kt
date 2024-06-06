package ru.skubatko.dev.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    fun objectMapper() = OBJECT_MAPPER
}
