package ru.skubatko.dev.user.service

import ru.skubatko.dev.user.exceptions.UserNotFoundException
import ru.skubatko.dev.user.mappers.toDto
import ru.skubatko.dev.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findByLogin(login: String) =
        userRepository
            .findByLogin(login)
            ?.toDto()
            ?: throw UserNotFoundException("User not found")
}
