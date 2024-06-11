package ru.skubatko.dev.hw02.rotate

interface Rotatable {
    fun getDirection(): Int
    fun setDirection(newValue: Int)
    fun getDirectionsNumber(): Int
    fun getAngularVelocity(): Int
}
