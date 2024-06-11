package ru.skubatko.dev.hw07

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw07.commands.HardStopServerThreadCommand
import ru.skubatko.dev.hw07.commands.SoftStopServerThreadCommand
import ru.skubatko.dev.hw07.commands.StartServerThreadCommand
import ru.skubatko.dev.test.utils.verifyNotCalled
import ru.skubatko.dev.test.utils.verifyOnce
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.awaitility.Duration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.ArrayBlockingQueue

@DisplayName("Сервер выполнения команд")
@ExtendWith(MockKExtension::class)
class ServerThreadTest {
    @RelaxedMockK
    private lateinit var command: Command

    private val q = ArrayBlockingQueue<Command>(10)
    private lateinit var serverThread: ServerThread

    @BeforeEach
    fun setUp() {
        serverThread = ServerThread(q)
    }

    @AfterEach
    fun tearDown() {
        serverThread.interrupt()
        q.clear()
    }

    @DisplayName("должен запускать сервер по команде start")
    @Test
    fun `should run server on start command`() {
        // given
        val startCommand = StartServerThreadCommand(serverThread)
        q.add(command)

        // when
        startCommand.execute()

        // then
        await().atMost(Duration.FIVE_SECONDS)
            .untilAsserted { verifyOnce { command.execute() } }
    }

    @DisplayName("должен ожидаемо завершать работу сервера по команде hard stop")
    @Test
    fun `should expectedly stop server on hard stop command`() {
        // given
        val hardStopCommand = HardStopServerThreadCommand(serverThread)
        q.add(hardStopCommand)
        q.add(command)

        var isStopped = false
        serverThread.setHookAfter { isStopped = true }

        // when
        StartServerThreadCommand(serverThread).execute()

        // then
        await().atMost(Duration.FIVE_SECONDS)
            .untilAsserted { assertThat(isStopped).isTrue() }
        verifyNotCalled { command.execute() }
    }

    @DisplayName("должен ожидаемо завершать работу сервера по команде soft stop")
    @Test
    fun `should expectedly stop server on soft stop command`() {
        // given
        val softStopCommand = SoftStopServerThreadCommand(serverThread, q)
        q.add(softStopCommand)
        q.add(command)

        var isStopped = false
        serverThread.setHookAfter { isStopped = true }

        // when
        StartServerThreadCommand(serverThread).execute()

        // then
        await().atMost(Duration.FIVE_SECONDS)
            .untilAsserted { assertThat(isStopped).isTrue() }
        assertThat(q).isEmpty()
        verifyOnce { command.execute() }
    }
}
