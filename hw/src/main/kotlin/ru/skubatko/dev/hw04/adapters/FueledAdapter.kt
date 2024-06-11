package ru.skubatko.dev.hw04.adapters

import ru.skubatko.dev.hw04.domain.Fueled
import ru.skubatko.dev.hw04.domain.Liter
import ru.skubatko.dev.hw04.domain.UObject

class FueledAdapter(
    private val o: UObject
) : Fueled {

    override fun getVolume() = o.getProperty("volume") as Liter

    override fun setVolume(newValue: Liter) {
        o.setProperty("volume", newValue)
    }

    override fun getConsumption() = o.getProperty("consumption") as Liter
}
