package ru.skubatko.dev.hw02

interface Movable {
    fun getPosition(): Vector
    fun setPosition(newValue: Vector)
    fun getVelocity(): Vector
}
