package ru.skubatko.dev.auth.config

import ru.skubatko.dev.auth.service.AuthUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    fun objectMapper() = OBJECT_MAPPER

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(
        passwordEncoder: PasswordEncoder,
        userDetailsService: AuthUserDetailsService
    ) =
        ProviderManager(
            DaoAuthenticationProvider(passwordEncoder).apply {
                setUserDetailsService(userDetailsService)
            })
}
