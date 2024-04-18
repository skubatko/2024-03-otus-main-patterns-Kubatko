package ru.skubatko.dev.hw02.move

class Move(private val movable: Movable) {

    fun execute() {
        movable.setPosition(movable.getPosition() + movable.getVelocity())
    }
}
