package ru.skubatko.dev.hw04.commands

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.exceptions.CommandException

class CheckMoveCommand<T : Movable>(private val t: T) : Command {

    override fun execute() {
        if (t.getVelocity().isZero) {
            throw CommandException()
        }
    }
}
