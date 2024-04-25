package ru.skubatko.dev.hw02.rotate

import ru.skubatko.dev.hw03.Command

class RotateCommand(private val rotatable: Rotatable) : Command {

    override fun execute() {
        rotatable.setDirection(
            (rotatable.getDirection() + rotatable.getAngularVelocity()) % rotatable.getDirectionsNumber()
        )
    }
}
