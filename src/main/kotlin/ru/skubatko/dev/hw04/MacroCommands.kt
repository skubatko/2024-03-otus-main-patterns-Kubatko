package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.move.MoveCommand
import ru.skubatko.dev.hw03.Command

sealed class MacroCommand(private val commandList: List<Command>) : Command {
    override fun execute() {
        try {
            commandList.forEach(Command::execute)
        } catch (ex: Exception) {
            throw CommandException()
        }
    }
}

class MoveBurnFuelMacroCommand<T>(t: T) : MacroCommand(
    listOf(CheckFuelCommand(t), MoveCommand(t), BurnFuelCommand(t))
) where T : Movable, T : Fueled
