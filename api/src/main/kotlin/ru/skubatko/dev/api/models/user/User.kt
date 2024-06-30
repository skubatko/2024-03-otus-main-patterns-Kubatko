package ru.skubatko.dev.api.models.user

data class User(
    val name: Username,
    val authority: UserAuthority
)
