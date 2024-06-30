package ru.skubatko.dev.jwt.mappers

import ru.skubatko.dev.api.models.jwt.JwtGenerationReqDto
import ru.skubatko.dev.api.models.user.User
import ru.skubatko.dev.api.models.user.UserAuthority
import ru.skubatko.dev.api.models.user.Username

fun JwtGenerationReqDto.toUser() =
    User(
        name = this.username?.let { Username(it) } ?: Username.DEFAULT,
        authority = this.authority?.let { UserAuthority(it) } ?: UserAuthority.DEFAULT
    )
