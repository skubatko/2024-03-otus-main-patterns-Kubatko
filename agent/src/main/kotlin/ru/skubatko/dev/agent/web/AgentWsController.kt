package ru.skubatko.dev.agent.web

import ru.skubatko.dev.api.models.game.GameStatusMessageTO
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import ru.sokomishalov.commons.core.log.Loggable

@Controller
class AgentWsController {

    @MessageMapping("/game/status")
    fun getGameStatus(gameStatusMessage: GameStatusMessageTO) {
        logInfo { "getGameStatus() - info: gameStatusMessage: $gameStatusMessage" }
    }

    companion object : Loggable
}
