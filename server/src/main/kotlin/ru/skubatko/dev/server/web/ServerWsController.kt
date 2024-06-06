package ru.skubatko.dev.server.web

import ru.skubatko.dev.api.models.CommandMessageTO
import ru.skubatko.dev.api.models.GameStatusMessageTO
import ru.skubatko.dev.server.service.GameService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import ru.sokomishalov.commons.core.log.Loggable

@Controller
class ServerWsController(
    private val gameService: GameService
) {

    @MessageMapping("/status")
    @SendTo("/game/status")
    fun getStatus(): GameStatusMessageTO {
        logInfo { "getStatus() - start" }
        val status = gameService.getStatus()
        logInfo { "getStatus() - end: status: $status" }
        return status
    }

    @MessageMapping("/command")
    fun handleCommand(msg: CommandMessageTO) {
        logInfo { "handleCommand() - start: msg: $msg" }
    }

    companion object : Loggable
}
