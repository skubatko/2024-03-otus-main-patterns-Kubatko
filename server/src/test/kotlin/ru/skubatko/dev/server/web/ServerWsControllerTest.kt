package ru.skubatko.dev.server.web

import ru.skubatko.dev.api.models.CommandMessageTO
import ru.skubatko.dev.api.models.GameStatusMessageTO
import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw05.Args
import ru.skubatko.dev.hw05.Dependency
import ru.skubatko.dev.hw05.Dependency.Companion.REGISTER
import ru.skubatko.dev.hw05.Dependency.Companion.SET_CURRENT_SCOPE
import ru.skubatko.dev.hw05.InitCommand
import ru.skubatko.dev.hw05.IoC
import ru.skubatko.dev.hw05.Scope
import ru.skubatko.dev.hw08.commands.InterpretCommand
import ru.skubatko.dev.hw08.domain.Game
import ru.skubatko.dev.hw08.domain.Operation
import ru.skubatko.dev.server.service.GameService
import ru.skubatko.dev.server.test.utils.verifyOnce
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Контроллер обмена по WevSocks")
@ExtendWith(MockKExtension::class)
class ServerWsControllerTest {
    @RelaxedMockK
    private lateinit var gameService: GameService

    @InjectMockKs
    private lateinit var serverWsController: ServerWsController

    @RelaxedMockK
    private val game = mockk<Game>(relaxed = true)
    @RelaxedMockK
    private val obj = mockk<UObject>(relaxed = true)
    @RelaxedMockK
    private val operation = mockk<Operation>(relaxed = true)
    @RelaxedMockK
    private val putInQueueCmd = mockk<Command>(relaxed = true)

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(Dependency.CREATE_SCOPE)
        IoC.resolve<Command>(SET_CURRENT_SCOPE, iocScope).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(Dependency.CLEAR_CURRENT_SCOPE).execute()
    }

    @DisplayName("должен возвращать статус игры по запросу")
    @Test
    fun `should replay with game status by request`() {
        // given
        val status = GameStatusMessageTO()
        every { gameService.getStatus() } returns status

        // when
        val actual = serverWsController.getStatus()

        // then
        assertThat(actual).isEqualTo(actual)
        verifyOnce { gameService.getStatus() }
    }

    @DisplayName("должен принимать входящее сообщение и конвертировать в команду InterpretCommand")
    @Test
    fun `should receive incoming message and convert into InterpretCommand`() {
        // given
        IoC.resolve<Command>(REGISTER, Dependency("Игра"), { _: Any -> game }).execute()
        IoC.resolve<Command>(REGISTER, Dependency("Игровой объект"), { _: Any -> obj }).execute()
        IoC.resolve<Command>(REGISTER, Dependency("Игровая операция"), { _: Any -> operation }).execute()

        var capturedInterpretCmd: InterpretCommand? = null
        var capturedGame: Game? = null
        val commandQueueStrategy = { args: Args ->
            capturedInterpretCmd = args[0] as InterpretCommand
            capturedGame = args[1] as Game
            putInQueueCmd
        }
        IoC.resolve<Command>(REGISTER, Dependency("Очередь команд"), commandQueueStrategy).execute()

        val msg = CommandMessageTO(1, 11, 111, emptyList())

        // when
        serverWsController.handleCommand(msg)

        // then
        assertThat(capturedInterpretCmd).isEqualTo(InterpretCommand(game, obj, operation, emptyList()))
        assertThat(capturedGame).isEqualTo(game)

        verifyOnce { putInQueueCmd.execute() }
    }
}
