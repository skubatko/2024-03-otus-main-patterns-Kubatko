package ru.skubatko.dev.api.models.user

data class UserDto(
    val name: String?,
    val login: String?,
    val password: String?,
    val role: UserRole?
)
