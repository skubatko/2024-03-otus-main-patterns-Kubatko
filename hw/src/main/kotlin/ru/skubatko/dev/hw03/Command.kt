package ru.skubatko.dev.hw03

import org.slf4j.Logger
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

class LogExceptionCommand(
    private val c: Command,
    private val ex: Exception,
    private val logger: Logger
) : Command {

    override fun execute() {
        logInfo { "log" }
        logger.error("error in command: ${c.javaClass.simpleName}", ex)
    }

    companion object : Loggable
}

class QueueLogExceptionCommand(
    private val c: Command,
    private val ex: Exception,
    private val queue: Queue<Command>,
    private val logger: Logger
) : Command {

    override fun execute() {
        logInfo { "put log in queue" }
        queue.offer(LogExceptionCommand(c, ex, logger))
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

class RetryCommand(private val c: Command) : Command {

    override fun execute() {
        logInfo { "retry" }
        c.execute()
    }

    companion object : Loggable
}

class QueueRetryCommand(
    private val c: Command,
    private val queue: Queue<Command>
) : Command {

    override fun execute() {
        logInfo { "queue retry" }
        queue.offer(RetryCommand(c))
    }

    companion object : Loggable
}

class RetryAndLogCommand(
    private val c: Command,
    private val logger: Logger
) : Command {

    override fun execute() {
        logInfo { "retry and log" }
        try {
            c.execute()
        } catch (ex: Exception) {
            logger.error("retry failed", ex)
        }
    }

    companion object : Loggable
}

class DoubleRetryAndLogCommand(
    private val c: Command,
    private val logger: Logger
) : Command {

    override fun execute() {
        logInfo { "double retry and log" }
        try {
            c.execute()
        } catch (ex: Exception) {
            try {
                c.execute()
            } catch (ex: Exception) {
                logger.error("double retry failed", ex)
            }
        }
    }

    companion object : Loggable
}
