package ru.skubatko.dev.hw04.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.Fueled

class BurnFuelCommand(private val fueled: Fueled) : Command {

    override fun execute() {
        fueled.setVolume(fueled.getVolume() - fueled.getConsumption())
    }
}
