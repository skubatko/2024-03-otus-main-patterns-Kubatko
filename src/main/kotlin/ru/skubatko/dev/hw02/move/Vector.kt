package ru.skubatko.dev.hw02.move

import kotlin.math.sqrt

data class Vector(val x: Int, val y: Int) {

    val length: Int
        get() = sqrt((x * x + y * y).toDouble()).toInt()

    val isZero: Boolean
        get() = this == ZERO

    operator fun plus(v: Vector) =
        Vector(this.x + v.x, this.y + v.y)

    companion object {
        val ZERO = Vector(0, 0)
    }
}
