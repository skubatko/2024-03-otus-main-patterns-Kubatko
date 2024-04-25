package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw03.Command

class BurnFuelCommand(private val fueled: Fueled) : Command {

    override fun execute() {
        fueled.setVolume(fueled.getVolume() - fueled.getConsumption())
    }
}
