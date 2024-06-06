package ru.skubatko.dev.agent.web

import ru.skubatko.dev.agent.service.AgentService
import ru.skubatko.dev.api.models.CommandMessageTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AgentRestController(
    private val agentService: AgentService
) {

    @PostMapping("/game/status")
    fun requestGameStatus() =
        agentService.requestGameStatus()

    @PostMapping("/game/command")
    fun sendGameCommand(@RequestBody msg: CommandMessageTO) {
        agentService.sendCommand(msg)
    }
}
