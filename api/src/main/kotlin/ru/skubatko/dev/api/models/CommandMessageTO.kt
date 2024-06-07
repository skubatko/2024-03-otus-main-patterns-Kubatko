package ru.skubatko.dev.api.models

data class CommandMessageTO(
    val gameId: Int,
    val objectId: Int,
    val operationId: Int,
    val args: List<Any>
)
