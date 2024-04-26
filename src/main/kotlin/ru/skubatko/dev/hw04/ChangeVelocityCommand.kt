package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw02.rotate.Rotatable
import ru.skubatko.dev.hw03.Command
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin

class ChangeVelocityCommand<T>(
    private val t: T
) : Command where T : Movable, T : Rotatable {

    override fun execute() {
        val d = t.getDirection()
        val n = t.getDirectionsNumber()
        val speed = t.getVelocity().length
        t.setVelocity(
            Vector(
                (speed * cos(2 * PI * d / n)).toInt(),
                (speed * sin(2 * PI * d / n)).toInt()
            )
        )
    }
}
