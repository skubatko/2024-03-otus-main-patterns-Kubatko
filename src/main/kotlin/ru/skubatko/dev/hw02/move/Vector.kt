package ru.skubatko.dev.hw02.move

data class Vector(val x: Int, val y: Int) {

    val isZero: Boolean
        get() = this.x == 0 && this.y == 0

    operator fun plus(v: Vector) =
        Vector(this.x + v.x, this.y + v.y)
}
