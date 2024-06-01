package ru.skubatko.dev.hw03

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import java.util.Queue

@DisplayName("Обработчик ошибок")
@ExtendWith(MockKExtension::class)
class ExceptionHandlerTest {
    @RelaxedMockK lateinit var queue: Queue<Command>
    @RelaxedMockK lateinit var logger: Logger
    @MockK lateinit var command: Command
    @MockK lateinit var ex: Exception

    @DisplayName("должен ставить Команду, пишущую в лог, в очередь Команд")
    @Test
    fun `should put log command into queue`() {
        // given
        val handler = QueueLogExceptionHandler(queue, logger)

        // when
        handler.handle(command, ex).execute()

        // then
        verify { queue.offer(any(LogExceptionCommand::class)) }
    }

    @DisplayName("должен ставить в очередь Команду - повторитель команды, выбросившей исключение")
    @Test
    fun `should put retry command into queue`() {
        // given
        val handler = QueueRetryExceptionHandler(queue)

        // when
        handler.handle(command, ex).execute()

        // then
        verify { queue.offer(any(RetryCommand::class)) }
    }

    @DisplayName("должен повторять команду и логировать ошибку")
    @Test
    fun `should retry command and log error`() {
        // given
        val handler = RetryAndLogExceptionHandler(logger)
        every { command.execute() } throws ex

        // when
        handler.handle(command, ex).execute()

        // then
        verifyOrder {
            command.execute()
            logger.error(any(), ex)
        }
    }

    @DisplayName("должен повторять команду дважды и логировать ошибку")
    @Test
    fun `should twice retry command and log error`() {
        // given
        val handler = DoubleRetryAndLogExceptionHandler(logger)
        every { command.execute() } throws ex

        // when
        handler.handle(command, ex).execute()

        // then
        verifyOrder {
            command.execute()
            command.execute()
            logger.error(any(), ex)
        }
    }
}
