package ru.skubatko.dev.api

data class AgentMessage(
    val gameId: GamedId,
    val objectId: ObjectId,
    val operationId: OperationId,
    val operationArgs: OperationArgs
)

@JvmInline
value class GamedId(private val value: Int)

@JvmInline
value class ObjectId(private val value: Int)

@JvmInline
value class OperationId(private val value: Int)

@JvmInline
value class OperationArgs(private val value: List<Any>)
