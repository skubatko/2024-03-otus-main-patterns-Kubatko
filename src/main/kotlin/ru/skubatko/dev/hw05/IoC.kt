@file:Suppress("UNCHECKED_CAST")

package ru.skubatko.dev.hw05

class IoC {

    companion object {
        private val DEFAULT_STRATEGY: IoCStrategy =
            { dependency: Dependency, args: Args ->
                if (dependency != Dependency("Update IoC Resolve Dependency Strategy")) {
                    throw IllegalArgumentException("Dependency $dependency is not found")
                }

                UpdateIocResolveDependencyStrategyCommand(args[0] as IoCStrategyUpdater)
            }
        var strategy = DEFAULT_STRATEGY

        fun <T> resolve(dependency: Dependency, vararg args: Any): T = strategy(dependency, args) as T
    }
}
