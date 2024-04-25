package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.rotate.Rotatable
import ru.skubatko.dev.hw03.Command

/**
 *
v = Rω
v/R = ω

ω1 - ω0 = v/R1 - v/R0
ω1 = ω0 + v(1/R1 - 1/R0)

R0 = ω0*v
R1 = sqrt(dx2+dy2)

ω1 = ω0 + v(1/R1 - 1/ω0*1/v)

ω1 = ω0 + v/R1 - v/ω0

ω1 = ω0 + v/sqrt(dx2+dy2) - v/ω0

 */

class UpdateAngularVelocityRotateCommand<T>(
    private val t: T,
    private val oldDirection: Int
) : Command where T : Movable, T : Rotatable {

    override fun execute() {
        t.setAngularVelocity(t.getDirection() - oldDirection)
    }
}
