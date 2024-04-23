package ru.skubatko.dev.hw03

import ru.sokomishalov.commons.core.log.Loggable
import java.util.Queue
import java.util.concurrent.atomic.AtomicBoolean

interface Command {
    fun execute()
}

class StopCommand(private val stop: AtomicBoolean) : Command {

    override fun execute() {
        logInfo { "stop" }
        stop.set(true)
    }

    companion object : Loggable
}

class LogExceptionHandlerCommand(
    private val c: Command,
    private val ex: Exception
) : Command {

    override fun execute() {
        logInfo { "log" }
        logError(ex) { "error in command: ${c.javaClass.simpleName}" }
    }

    companion object : Loggable
}

class QueueLogExceptionHandlerCommand(
    private val c: Command,
    private val ex: Exception,
    private val queue: Queue<Command>
) : Command {

    override fun execute() {
        logInfo { "put log in queue" }
        queue.offer(LogExceptionHandlerCommand(c, ex))
    }

    companion object : Loggable
}

class WaitCommand : Command {

    override fun execute() {
        logInfo { "wait" }
        Thread.sleep(1000)
    }

    companion object : Loggable
}

class RuntimeExceptionCommand : Command {

    override fun execute() {
        logInfo { "runtime exception" }
        throw RuntimeException("error")
    }

    companion object : Loggable
}

class IllegalArgumentExceptionCommand : Command {

    override fun execute() {
        logInfo { "illegal argument exception" }
        throw IllegalArgumentException("arg error")
    }

    companion object : Loggable
}

class IndexOutOfBoundsExceptionCommand : Command {

    override fun execute() {
        logInfo { "index out of bounds exception" }
        throw IndexOutOfBoundsException("index error")
    }

    companion object : Loggable
}

class RetryCommand(private val c: Command) : Command {

    override fun execute() {
        logInfo { "retry" }
        try {
            c.execute()
        } catch (ex: Exception) {
            logError(ex) { "retry failed" }
        }
    }

    companion object : Loggable
}

class QueueRetryCommand(
    private val c: Command,
    private val queue: Queue<Command>
) : Command {

    override fun execute() {
        logInfo { "queue retry" }
        queue.offer(c)
    }

    companion object : Loggable
}
