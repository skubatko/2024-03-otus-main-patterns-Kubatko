package ru.skubatko.dev.user.config

import ru.skubatko.dev.api.models.user.UserRole
import ru.skubatko.dev.user.entity.UserEntity
import ru.skubatko.dev.user.repository.UserRepository
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DbPopulation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @EventListener(ContextRefreshedEvent::class)
    fun populate() {
        userRepository.saveAll(
            listOf(
                UserEntity().apply {
                    login = "admin"
                    name = "admin"
                    password = passwordEncoder.encode("123")
                    role = UserRole.ROLE_ADMIN
                },
                UserEntity().apply {
                    login = "user"
                    name = "user"
                    password = passwordEncoder.encode("123")
                    role = UserRole.ROLE_USER
                }
            )
        )
    }
}
