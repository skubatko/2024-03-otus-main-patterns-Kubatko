package ru.skubatko.dev.hw03

import org.slf4j.Logger
import ru.sokomishalov.commons.core.log.Loggable
import java.util.Queue
import kotlin.reflect.KClass

typealias Handler = (Command, Exception) -> Command
typealias CommandType = KClass<out Command>
typealias ExceptionType = KClass<out Exception>

class ExceptionHandler {

    companion object : Loggable {
        private val store: MutableMap<CommandType, MutableMap<ExceptionType, Handler>> =
            mutableMapOf()

        fun handle(c: Command, ex: Exception): Command =
            (store[c::class]?.get(ex::class))?.invoke(c, ex) ?: LogExceptionCommand(c, ex, logger)

        fun register(cType: CommandType, exType: ExceptionType, handler: Handler) {
            store.getOrPut(cType) { mutableMapOf() }[exType] = handler
        }
    }
}

class QueueLogExceptionHandler(
    private val queue: Queue<Command>,
    private val logger: Logger
) {
    fun handle(c: Command, ex: Exception) = QueueLogExceptionCommand(c, ex, queue, logger)
}

class RetryExceptionHandler {
    fun handle(c: Command, ex: Exception) = RetryCommand(c)
}

class QueueRetryExceptionHandler(
    private val queue: Queue<Command>
) {
    fun handle(c: Command, ex: Exception) = QueueRetryCommand(c, queue)
}

class RetryAndLogExceptionHandler(
    private val logger: Logger
) {
    fun handle(c: Command, ex: Exception) = RetryAndLogCommand(c, logger)
}

class DoubleRetryAndLogExceptionHandler(
    private val logger: Logger
) {
    fun handle(c: Command, ex: Exception) = DoubleRetryAndLogCommand(c, logger)
}
