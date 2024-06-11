package ru.skubatko.dev.hw04.commands

import ru.skubatko.dev.hw02.move.MoveCommand
import ru.skubatko.dev.hw02.rotate.RotateCommand
import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.adapters.FueledAdapter
import ru.skubatko.dev.hw04.adapters.MovableAdapter
import ru.skubatko.dev.hw04.adapters.RotatableAdapter
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw04.exceptions.CommandException

abstract class MacroCommand(private val commandList: List<Command>) : Command {
    override fun execute() {
        try {
            commandList.forEach(Command::execute)
        } catch (ex: Exception) {
            throw CommandException()
        }
    }
}

class MoveBurnFuelMacroCommand<T : UObject>(t: T) : MacroCommand(
    listOf(
        CheckFuelCommand(FueledAdapter(t)),
        MoveCommand(MovableAdapter(t)),
        BurnFuelCommand(FueledAdapter(t))
    )
)

class MoveRotateMacroCommand<T : UObject>(t: T) : MacroCommand(
    listOf(
        CheckMoveCommand(MovableAdapter(t)),
        RotateCommand(RotatableAdapter(t)),
        ChangeVelocityCommand(t)
    )
)
