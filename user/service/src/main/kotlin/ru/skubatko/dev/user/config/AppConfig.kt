package ru.skubatko.dev.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun objectMapper() = OBJECT_MAPPER
}
