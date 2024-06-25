package ru.skubatko.dev.server.config

import ru.skubatko.dev.server.user.User
import ru.skubatko.dev.server.user.UserRepository
import ru.skubatko.dev.server.user.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER

@Configuration(proxyBeanMethods = false)
class AppConfig(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @EventListener(ContextRefreshedEvent::class)
    fun authInit() {
        userRepository.saveAll(
            listOf(
                User().apply {
                    login = "admin"
                    name = "admin"
                    pswd = passwordEncoder.encode("123")
                    role = UserRole.ROLE_ADMIN
                },
                User().apply {
                    login = "user"
                    name = "user"
                    pswd = passwordEncoder.encode("123")
                    role = UserRole.ROLE_USER
                }
            )
        )
    }

    @Bean
    fun objectMapper() = OBJECT_MAPPER
}
