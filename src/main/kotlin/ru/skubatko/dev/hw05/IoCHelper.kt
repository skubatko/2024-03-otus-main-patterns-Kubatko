package ru.skubatko.dev.hw05

import java.util.concurrent.ConcurrentHashMap

typealias Args = Array<out Any>
typealias IoCStrategyUpdater = (IoCStrategy) -> IoCStrategy
typealias IoCStrategy = (Dependency, Args) -> Any
typealias DependencyResolverStrategy = (Args) -> Any

class Scope {
    private val scope = ConcurrentHashMap<Dependency, DependencyResolverStrategy>()

    operator fun get(dependency: Dependency): DependencyResolverStrategy? = scope[dependency]

    operator fun set(dependency: Dependency, strategy: DependencyResolverStrategy) {
        scope[dependency] = strategy
    }
}
@JvmInline
value class Dependency(private val value: String) {
    companion object {
        val REGISTER = Dependency("IoC.Register")
        val STRATEGY_UPDATE = Dependency("Update IoC Resolve Dependency Strategy")
        val CREATE_SCOPE = Dependency("IoC.Scope.Create")
        val CREATE_EMPTY_SCOPE = Dependency("IoC.Scope.Create.Empty")
        val SET_CURRENT_SCOPE = Dependency("IoC.Scope.Current.Set")
        val CLEAR_CURRENT_SCOPE = Dependency("IoC.Scope.Current.Clear")
        val PARENT_SCOPE = Dependency("IoC.Scope.Parent")
        val CURRENT_SCOPE = Dependency("IoC.Scope.Current")
    }
}
