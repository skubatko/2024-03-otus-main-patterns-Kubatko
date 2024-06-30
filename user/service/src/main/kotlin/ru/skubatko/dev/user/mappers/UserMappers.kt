package ru.skubatko.dev.user.mappers

import ru.skubatko.dev.api.models.user.UserDto
import ru.skubatko.dev.user.entity.UserEntity

fun UserEntity.toDto() =
    UserDto(
        name = this.name,
        login = this.login,
        password = this.password,
        role = this.role
    )
