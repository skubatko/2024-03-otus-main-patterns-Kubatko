package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command

class UpdateIocResolveDependencyStrategyCommand(
    private val updater: IoCStrategyUpdater
) : Command {

    override fun execute() {
        IoC.strategy = updater(IoC.strategy)
    }
}
