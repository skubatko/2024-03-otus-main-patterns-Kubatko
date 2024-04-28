package ru.skubatko.dev.hw04.commands

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.adapters.MovableAdapter
import ru.skubatko.dev.hw04.domain.UObject

class ChangeVelocityCommand<T : UObject>(private val t: T) : Command {

    override fun execute() {
        val a = MovableAdapter(t)
        a.setPosition(a.getPosition() + a.getVelocity())
    }
}
