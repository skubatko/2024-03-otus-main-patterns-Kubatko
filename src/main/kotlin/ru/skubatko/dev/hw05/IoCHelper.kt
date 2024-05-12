package ru.skubatko.dev.hw05

import java.util.concurrent.ConcurrentHashMap

typealias Args = Array<out Any>
typealias IoCStrategyUpdater = (IoCStrategy) -> IoCStrategy
typealias IoCStrategy = (Dependency, Args) -> Any
typealias DependencyResolverStrategy = (Args) -> Any

@JvmInline
value class Dependency(private val value: String)

class Scope {
    private val scope = ConcurrentHashMap<Dependency, DependencyResolverStrategy>()

    operator fun get(dependency: Dependency): DependencyResolverStrategy? = scope[dependency]

    operator fun set(dependency: Dependency, strategy: DependencyResolverStrategy) {
        scope[dependency] = strategy
    }
}
