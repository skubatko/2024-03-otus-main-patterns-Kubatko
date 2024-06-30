package ru.skubatko.dev.api.models.jwt

data class JwtValidationReqDto(
    val token: String?,
    val username: String,
)
