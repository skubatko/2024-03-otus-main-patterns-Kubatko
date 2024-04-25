package ru.skubatko.dev.hw02.move

interface Movable {
    fun getPosition(): Vector
    fun setPosition(newValue: Vector)
    fun getVelocity(): Vector
    fun setVelocity(newValue: Vector)
}
