package ru.skubatko.dev.hw03

import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger

@DisplayName("Набор команд")
@ExtendWith(MockKExtension::class)
class CommandTest {
    @RelaxedMockK lateinit var logger: Logger
    @RelaxedMockK lateinit var c: Command
    @MockK lateinit var ex: Exception

    @DisplayName("должен содержать Команду, которая записывает информацию о выброшенном исключении в лог")
    @Test
    fun `should have command that log exception`() {
        // given
        val command = LogExceptionCommand(c, ex, logger)

        // when
        command.execute()

        // then
        verify { logger.error(any(), ex) }
    }

    @DisplayName("должен содержать Команду, которая повторяет Команду, выбросившую исключение")
    @Test
    fun `should have command that retry command thrown exception`() {
        // given
        val command = RetryCommand(c)

        // when
        command.execute()

        // then
        verify { c.execute() }
    }
}
