package ru.skubatko.dev.hw04.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.Fueled
import ru.skubatko.dev.hw04.exceptions.CommandException

class CheckFuelCommand(private val fueled: Fueled) : Command {

    override fun execute() {
        if ((fueled.getVolume() - fueled.getConsumption()).isExcessive)
            throw CommandException()
    }
}
