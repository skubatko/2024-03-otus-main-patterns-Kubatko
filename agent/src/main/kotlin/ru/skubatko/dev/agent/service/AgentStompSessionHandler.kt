package ru.skubatko.dev.agent.service

import ru.skubatko.dev.api.models.game.GameStatusMessageTO
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import ru.sokomishalov.commons.core.log.Loggable
import java.lang.reflect.Type

@Component
class AgentStompSessionHandler(
    private val gameService: GameService
) : StompSessionHandler {

    override fun getPayloadType(headers: StompHeaders): Type =
        GameStatusMessageTO::class.java

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        logInfo { "handleFrame() - start: game status: $payload" }
        gameService.handleGameStatus(payload as GameStatusMessageTO)
        logInfo { "handleFrame() - end" }
    }

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        session.subscribe("/game/status", this);
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        // nothing
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        // nothing
    }

    companion object : Loggable
}
