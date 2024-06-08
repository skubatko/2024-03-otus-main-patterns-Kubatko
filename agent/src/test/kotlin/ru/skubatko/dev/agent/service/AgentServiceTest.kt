package ru.skubatko.dev.agent.service

import ru.skubatko.dev.agent.test.utils.verifyOnce
import ru.skubatko.dev.api.models.CommandMessageTO
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.messaging.simp.stomp.StompSession

@DisplayName("Сервис агента")
@ExtendWith(MockKExtension::class)
class AgentServiceTest {
    @RelaxedMockK
    private lateinit var stompSession: StompSession

    @InjectMockKs
    private lateinit var agentService: AgentService

    @DisplayName("должен запрашивать статус игры")
    @Test
    fun `should request game status`() {
        agentService.requestGameStatus()

        verifyOnce { stompSession.send("/server/status", "") }
    }

    @DisplayName("должен направлять команду")
    @Test
    fun `should send command`() {
        val msg = mockk<CommandMessageTO>()
        agentService.sendCommand(msg)

        verifyOnce { stompSession.send("/server/command", msg) }
    }
}
