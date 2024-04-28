package ru.skubatko.dev.hw04.domain

@JvmInline
value class Liter(private val value: Int) {
    val isExcessive: Boolean
        get() = value < 0

    operator fun minus(consumption: Liter) = Liter(this.value - consumption.value)

    companion object {
        val DEFAULT = Liter(0)
    }
}
