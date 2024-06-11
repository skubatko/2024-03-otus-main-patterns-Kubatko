package ru.skubatko.dev.hw04.adapters

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.domain.UObject
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin

class MovableAdapter(
    private val o: UObject
) : Movable {

    override fun getPosition(): Vector = o.getProperty("position") as Vector

    override fun setPosition(newValue: Vector) {
        o.setProperty("position", newValue)
    }

    override fun getVelocity(): Vector {
        val d = o.getProperty("direction") as Int
        val n = o.getProperty("directionsNumber") as Int
        val v = o.getProperty("velocity") as Int
        val dRad = 2 * PI * d / n
        return Vector((v * cos(dRad)).toInt(), (v * sin(dRad)).toInt())
    }
}
