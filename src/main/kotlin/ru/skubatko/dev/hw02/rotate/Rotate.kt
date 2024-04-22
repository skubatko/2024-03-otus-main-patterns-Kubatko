package ru.skubatko.dev.hw02.rotate

class Rotate(
    private val rotatable: Rotatable
) {

    fun execute() {
        rotatable.setDirection(
            (rotatable.getDirection() + rotatable.getAngularVelocity()) % rotatable.getDirectionsNumber()
        )
    }
}
