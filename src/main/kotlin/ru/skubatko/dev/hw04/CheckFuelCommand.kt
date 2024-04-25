package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw03.Command

class CheckFuelCommand(private val fueled: Fueled) : Command {

    override fun execute() {
        if ((fueled.getVolume() - fueled.getConsumption()).isExcessive)
            throw CommandException()
    }
}
