package ru.skubatko.dev.hw03

import ru.sokomishalov.commons.core.log.CustomLoggerFactory
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

fun main() {
    val stop = AtomicBoolean(false)
    val queue = ArrayBlockingQueue<Command>(32)
    val logger = CustomLoggerFactory.getLogger("App")

    ExceptionHandler.register(
        RuntimeExceptionCommand::class,
        RuntimeException::class,
        // QueueLogExceptionHandler(queue,logger)::handle
        // RetryExceptionHandler()::handle
        QueueRetryExceptionHandler(queue)::handle
        // RetryAndLogExceptionHandler(logger)::handle
        // DoubleRetryAndLogExceptionHandler(logger)::handle
    )

    queue.offer(RuntimeExceptionCommand())
    repeat(3) { queue.offer(WaitCommand()) }

    thread {
        while (!stop.get()) {
            val command = queue.poll() ?: StopCommand(stop)
            try {
                command.execute()
            } catch (ex: Exception) {
                ExceptionHandler.handle(command, ex).execute()
            }
        }
    }.join()
}
