package ru.skubatko.dev.hw02.move

import ru.skubatko.dev.hw03.Command

class MoveCommand(private val movable: Movable) : Command {

    override fun execute() {
        movable.setPosition(
            movable.getPosition() + movable.getVelocity()
        )
    }
}
