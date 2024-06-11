package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.commands.MacroCommand
import ru.skubatko.dev.hw04.exceptions.CommandException
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Макрокоманда")
@ExtendWith(MockKExtension::class)
class MacroCommandTest {
    @RelaxedMockK lateinit var command1: Command
    @RelaxedMockK lateinit var command2: Command
    @RelaxedMockK lateinit var command3: Command

    lateinit var commandList: List<Command>
    lateinit var macroCommand: MacroCommand

    @BeforeEach
    fun setUp() {
        commandList = listOf(command1, command2, command3)
        macroCommand = object : MacroCommand(commandList) {}
    }

    @DisplayName("должна запускать выполнение подкоманд")
    @Test
    fun `should execute subcommands`() {
        // when
        macroCommand.execute()

        // then
        commandList.forEach {
            verify { it.execute() }
        }
    }

    @DisplayName("должна прерывать выполнение при сбое одной из подкоманд")
    @Test
    fun `should interrupt execution when any subcommand is failed`() {
        // given
        every { command2.execute() } throws RuntimeException()

        // when + then
        assertThatThrownBy { macroCommand.execute() }.isInstanceOf(CommandException::class.java)
    }
}
