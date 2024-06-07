package ru.skubatko.dev.agent.service

import ru.skubatko.dev.api.models.CommandMessageTO
import ru.skubatko.dev.api.models.GameStatusMessageTO
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.stereotype.Service

@Service
class AgentService(
    private val stompSession: StompSession
) {

    fun requestGameStatus() {
        stompSession.send("/server/status", "")
    }

    fun sendCommand(msg: CommandMessageTO) {
        stompSession.send("/server/command", msg)
    }

    fun handleGameStatus(gameStatus: GameStatusMessageTO) {
        // отправка статуса игры для принятия решения по следующему действию
        // например, это может быть отправка в kafka
    }
}
