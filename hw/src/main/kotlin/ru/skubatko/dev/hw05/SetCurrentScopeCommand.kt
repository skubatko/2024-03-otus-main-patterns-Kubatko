package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command

class SetCurrentScopeCommand(
    private val args: Args
) : Command {

    override fun execute() {
        InitCommand.currentScopes.set(args[0] as Scope)
    }
}
