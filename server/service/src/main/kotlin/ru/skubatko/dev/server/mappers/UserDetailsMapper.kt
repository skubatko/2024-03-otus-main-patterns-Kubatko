package ru.skubatko.dev.server.mappers

import ru.skubatko.dev.api.models.user.UserDto
import ru.skubatko.dev.server.models.GameUserDetails
import org.springframework.security.core.userdetails.UserDetails

fun UserDto.toUserDetails(): UserDetails =
    GameUserDetails(
        name = this.name,
        login = this.login,
        pswd = this.password,
        role = this.role
    )
