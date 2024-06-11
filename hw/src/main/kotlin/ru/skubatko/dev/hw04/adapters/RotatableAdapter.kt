package ru.skubatko.dev.hw04.adapters

import ru.skubatko.dev.hw02.rotate.Rotatable
import ru.skubatko.dev.hw04.domain.UObject

class RotatableAdapter(
    private val o: UObject
) : Rotatable {

    override fun getDirection(): Int = o.getProperty("direction") as Int

    override fun setDirection(newValue: Int) {
        o.setProperty("direction", newValue)
    }

    override fun getDirectionsNumber(): Int = o.getProperty("directionsNumber") as Int

    override fun getAngularVelocity(): Int = o.getProperty("angularVelocity") as Int
}
