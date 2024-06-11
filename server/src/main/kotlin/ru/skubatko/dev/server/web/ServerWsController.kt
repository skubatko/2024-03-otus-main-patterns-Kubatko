package ru.skubatko.dev.server.web

import ru.skubatko.dev.api.models.CommandMessageTO
import ru.skubatko.dev.api.models.GameStatusMessageTO
import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw05.Dependency
import ru.skubatko.dev.hw05.IoC
import ru.skubatko.dev.hw08.commands.InterpretCommand
import ru.skubatko.dev.hw08.domain.Game
import ru.skubatko.dev.hw08.domain.Operation
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
        val game = IoC.resolve<Game>(Dependency("Игра"), msg.gameId);
        val obj = IoC.resolve<UObject>(Dependency("Игровой объект"), msg.objectId);
        val operation = IoC.resolve<Operation>(Dependency("Игровая операция"), msg.operationId);
        val interpretCmd = InterpretCommand(game, obj, operation, msg.args)
        IoC.resolve<Command>(Dependency("Очередь команд"), interpretCmd, game).execute();
        logInfo { "handleCommand() - end" }
    }

    companion object : Loggable
}
