@file:Suppress("UNCHECKED_CAST")

package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw05.Dependency.Companion.STRATEGY_UPDATE

class IoC {

    companion object {
        private val DEFAULT_STRATEGY: IoCStrategy =
            { dependency: Dependency, args: Args ->
                if (dependency != STRATEGY_UPDATE) {
                    throw IllegalArgumentException("Dependency $dependency is not found")
                }

                UpdateIocResolveDependencyStrategyCommand(args[0] as IoCStrategyUpdater)
            }
        var strategy = DEFAULT_STRATEGY

        fun <T> resolve(dependency: Dependency, vararg args: Any): T = strategy(dependency, args) as T
    }
}
