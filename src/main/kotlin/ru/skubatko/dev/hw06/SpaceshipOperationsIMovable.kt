package ru.skubatko.dev.hw06

import ru.skubatko.dev.hw02.move.Vector

interface SpaceshipOperationsIMovable {
    fun getPosition(): Vector
    fun setPosition(newValue: Vector): Vector
    fun getVelocity(): Vector
}
