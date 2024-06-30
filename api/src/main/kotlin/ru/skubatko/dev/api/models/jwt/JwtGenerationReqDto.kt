package ru.skubatko.dev.api.models.jwt

data class JwtGenerationReqDto(
    val username: String?,
    val authority: String?,
)
