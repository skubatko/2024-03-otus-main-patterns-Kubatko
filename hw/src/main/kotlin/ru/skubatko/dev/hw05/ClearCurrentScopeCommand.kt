package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command

class ClearCurrentScopeCommand : Command {

    override fun execute() {
        InitCommand.currentScopes.remove()
    }
}
