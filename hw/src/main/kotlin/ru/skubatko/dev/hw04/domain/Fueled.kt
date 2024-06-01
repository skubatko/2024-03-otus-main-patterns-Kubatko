package ru.skubatko.dev.hw04.domain

interface Fueled {
    fun getVolume(): Liter
    fun setVolume(newValue: Liter)
    fun getConsumption(): Liter
}
