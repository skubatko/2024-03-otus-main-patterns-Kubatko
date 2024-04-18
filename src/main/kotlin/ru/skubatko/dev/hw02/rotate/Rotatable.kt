package ru.skubatko.dev.hw02.rotate

interface Rotatable {
    fun getDirection(): Int
    fun getAngularVelocity(): Int
    fun setDirection(newValue: Int)
    fun getDirectionsNumber(): Int
}
