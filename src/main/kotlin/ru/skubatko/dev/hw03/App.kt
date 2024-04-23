package ru.skubatko.dev.hw03

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

fun main() {
    val stop = AtomicBoolean(false)
    val queue = ArrayBlockingQueue<Command>(32)

    ExceptionHandler.register(
        RuntimeExceptionCommand::class,
        RuntimeException::class,
        QueueLogExceptionHandler(queue)::handle
    )

    ExceptionHandler.register(
        IllegalArgumentExceptionCommand::class,
        IllegalArgumentException::class,
        RetryExceptionHandler()::handle
    )

    ExceptionHandler.register(
        IndexOutOfBoundsExceptionCommand::class,
        IndexOutOfBoundsException::class,
        QueueRetryExceptionHandler(queue)::handle
    )

    queue.offer(RuntimeExceptionCommand())
    repeat(3) { queue.offer(WaitCommand()) }
    queue.offer(IllegalArgumentExceptionCommand())
    repeat(3) { queue.offer(WaitCommand()) }
    queue.offer(IndexOutOfBoundsExceptionCommand())
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
