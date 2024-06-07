package ru.skubatko.dev.hw08.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw05.Dependency
import ru.skubatko.dev.hw05.IoC
import ru.skubatko.dev.hw08.domain.Game
import ru.skubatko.dev.hw08.domain.Operation

class InterpretCommand(
    private val game: Game,
    private val obj: UObject,
    private val operation: Operation,
    private val args: List<Any>
) : Command {
    override fun execute() {
        val cmd = IoC.resolve<Command>(Dependency("Игровая команда"), obj, operation, args)
        IoC.resolve<Command>(Dependency("Очередь команд"), cmd, game).execute();
    }
}
