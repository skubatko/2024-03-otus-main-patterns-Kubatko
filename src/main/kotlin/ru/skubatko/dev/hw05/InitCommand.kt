@file:Suppress("UNCHECKED_CAST")

package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command

class InitCommand : Command {

    override fun execute() {
        if (alreadyExecutesSuccessfully) return;

        synchronized(rootScope) {
            rootScope[Dependency("IoC.Scope.Current.Set")] = { args -> SetCurrentScopeCommand(args) }
            rootScope[Dependency("IoC.Scope.Current.Clear")] = { ClearCurrentScopeCommand() }
            rootScope[Dependency("IoC.Scope.Current")] = { currentScopes.get() ?: rootScope }
            rootScope[Dependency("IoC.Scope.Parent")] =
                { throw RuntimeException("The root scope has no a parent scope") }
            rootScope[Dependency("IoC.Scope.Create.Empty")] = { Scope() }
            rootScope[Dependency("IoC.Scope.Create")] = { args ->
                IoC.resolve<Scope>(Dependency("IoC.Scope.Create.Empty")).also { creatingScope ->
                    val parentScope = when {
                        args.isNotEmpty() -> args[0]
                        else -> IoC.resolve<Scope>(Dependency("IoC.Scope.Current"))
                    }
                    creatingScope[Dependency("IoC.Scope.Parent")] = { parentScope }
                }
            }
            rootScope[Dependency("IoC.Register")] = { args ->
                RegisterDependencyCommand(
                    args[0] as Dependency,
                    args[1] as DependencyResolverStrategy
                )
            }

            IoC.resolve<Command>(
                Dependency("Update IoC Resolve Dependency Strategy"),
                { _: IoCStrategy ->
                    { dependency: Dependency, args: Args ->
                        val scope = currentScopes.get() ?: rootScope
                        val dependencyResolver = DependencyResolver(scope)
                        dependencyResolver.resolve(dependency, *args)
                    }
                }
            ).execute()

            alreadyExecutesSuccessfully = true
        }
    }

    companion object {
        private val rootScope = Scope()
        var currentScopes = ThreadLocal<Scope>()
        var alreadyExecutesSuccessfully = false
    }
}
