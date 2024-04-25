package ru.skubatko.dev.hw04

@JvmInline
value class Liter(private val value: Int) {
    val isExcessive: Boolean
        get() = value < 0

    operator fun minus(consumption: Liter) = Liter(this.value - consumption.value)
}
