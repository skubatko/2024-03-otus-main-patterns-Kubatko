@file:Suppress("UNCHECKED_CAST")

package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw05.Dependency.Companion.CLEAR_CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CREATE_EMPTY_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CREATE_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.PARENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.REGISTER
import ru.skubatko.dev.hw05.Dependency.Companion.SET_CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.STRATEGY_UPDATE

class InitCommand : Command {

    override fun execute() {
        if (alreadyExecutesSuccessfully) return;

        synchronized(rootScope) {
            rootScope[SET_CURRENT_SCOPE] = { args -> SetCurrentScopeCommand(args) }
            rootScope[CLEAR_CURRENT_SCOPE] = { ClearCurrentScopeCommand() }
            rootScope[CURRENT_SCOPE] = { currentScopes.get() ?: rootScope }
            rootScope[PARENT_SCOPE] =
                { throw RuntimeException("The root scope has no a parent scope") }
            rootScope[CREATE_EMPTY_SCOPE] = { Scope() }
            rootScope[CREATE_SCOPE] = { args ->
                IoC.resolve<Scope>(CREATE_EMPTY_SCOPE).also { creatingScope ->
                    val parentScope = when {
                        args.isNotEmpty() -> args[0]
                        else -> IoC.resolve<Scope>(CURRENT_SCOPE)
                    }
                    creatingScope[PARENT_SCOPE] = { parentScope }
                }
            }
            rootScope[REGISTER] = { args ->
                RegisterDependencyCommand(
                    args[0] as Dependency,
                    args[1] as DependencyResolverStrategy
                )
            }

            IoC.resolve<Command>(
                STRATEGY_UPDATE,
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
