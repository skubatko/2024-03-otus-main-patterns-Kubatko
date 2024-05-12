package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw05.Dependency.Companion.CURRENT_SCOPE

class RegisterDependencyCommand(
    private val dependency: Dependency,
    private val dependencyResolverStrategy: DependencyResolverStrategy
) : Command {

    override fun execute() {
        val currentScope = IoC.resolve<Scope>(CURRENT_SCOPE)
        currentScope[dependency] = dependencyResolverStrategy
    }
}
