package ru.skubatko.dev.auth.mappers

import ru.skubatko.dev.api.models.user.UserDto
import ru.skubatko.dev.auth.models.AuthUserDetails
import org.springframework.security.core.userdetails.UserDetails

fun UserDto.toUserDetails(): UserDetails =
    AuthUserDetails(
        name = this.name,
        login = this.login,
        pswd = this.password,
        role = this.role
    )
