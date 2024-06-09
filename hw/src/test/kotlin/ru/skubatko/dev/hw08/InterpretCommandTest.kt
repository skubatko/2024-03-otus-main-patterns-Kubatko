package ru.skubatko.dev.hw08

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw05.Dependency
import ru.skubatko.dev.hw05.Dependency.Companion.CLEAR_CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CREATE_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.REGISTER
import ru.skubatko.dev.hw05.Dependency.Companion.SET_CURRENT_SCOPE
import ru.skubatko.dev.hw05.InitCommand
import ru.skubatko.dev.hw05.IoC
import ru.skubatko.dev.hw05.Scope
import ru.skubatko.dev.hw08.commands.InterpretCommand
import ru.skubatko.dev.hw08.domain.Game
import ru.skubatko.dev.hw08.domain.Operation
import ru.skubatko.dev.test.utils.verifyOnce
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Команда InterpretCommand")
class InterpretCommandTest {
    private val game = mockk<Game>(relaxed = true)
    private val obj = mockk<UObject>(relaxed = true)
    private val operation = mockk<Operation>(relaxed = true)
    private val args = emptyList<Any>()
    private val cmd = mockk<Command>(relaxed = true)
    private val putInQueueCmd = mockk<Command>(relaxed = true)

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(CREATE_SCOPE)
        IoC.resolve<Command>(SET_CURRENT_SCOPE, iocScope).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(CLEAR_CURRENT_SCOPE).execute()
    }

    @DisplayName("должна создать команду и поставить её в очередь для выполнения")
    @Test
    fun `should create command and put it into the queue to execute`() {
        // given
        IoC.resolve<Command>(REGISTER, Dependency("Игровая команда"), { _: Any -> cmd }).execute()
        IoC.resolve<Command>(REGISTER, Dependency("Очередь команд"), { _: Any -> putInQueueCmd }).execute()
        val interpretCommand = InterpretCommand(game, obj, operation, args)

        // when
        interpretCommand.execute()

        // then
        verifyOnce { putInQueueCmd.execute() }
    }
}
