package ru.skubatko.dev.hw02

data class Vector(
    private val x: Int,
    private val y: Int
) {
    operator fun plus(v: Vector) =
        Vector(this.x + v.x, this.y + v.y)
}
