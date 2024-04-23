package ru.skubatko.dev.hw03

import java.util.Queue
import kotlin.reflect.KClass

typealias Handler = (Command, Exception) -> Command
typealias CommandType = KClass<out Command>
typealias ExceptionType = KClass<out Exception>

class ExceptionHandler {

    companion object {
        private val store: MutableMap<CommandType, MutableMap<ExceptionType, Handler>> =
            mutableMapOf()

        fun handle(c: Command, ex: Exception): Command =
            (store[c::class]?.get(ex::class))?.invoke(c, ex) ?: LogExceptionHandlerCommand(c, ex)

        fun register(cType: CommandType, exType: ExceptionType, handler: Handler) {
            store.getOrPut(cType) { mutableMapOf() }[exType] = handler
        }
    }
}

class QueueLogExceptionHandler(
    private val queue: Queue<Command>
) {
    fun handle(c: Command, ex: Exception): Command =
        QueueLogExceptionHandlerCommand(c, ex, queue)
}

class RetryExceptionHandler {
    fun handle(c: Command, ex: Exception): Command =
        RetryCommand(c)
}

class QueueRetryExceptionHandler(
    private val queue: Queue<Command>
) {
    fun handle(c: Command, ex: Exception): Command =
        QueueRetryCommand(c, queue)
}
