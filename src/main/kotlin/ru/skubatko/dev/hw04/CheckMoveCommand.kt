package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw03.Command

class CheckMoveCommand<T : Movable>(private val t: T) : Command {

    override fun execute() {
        if (t.getVelocity().isZero) {
            throw CommandException()
        }
    }
}
