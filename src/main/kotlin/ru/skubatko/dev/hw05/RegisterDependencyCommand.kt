package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command

class RegisterDependencyCommand(
    private val dependency: Dependency,
    private val dependencyResolverStrategy: DependencyResolverStrategy
) : Command {

    override fun execute() {
        val currentScope = IoC.resolve<Scope>(Dependency("IoC.Scope.Current"))
        currentScope[dependency] = dependencyResolverStrategy
    }
}
