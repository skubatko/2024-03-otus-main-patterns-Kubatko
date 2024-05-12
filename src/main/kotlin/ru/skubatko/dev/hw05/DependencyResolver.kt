package ru.skubatko.dev.hw05

class DependencyResolver(
    private val _scope: Scope
) {

    fun resolve(dependency: Dependency, vararg args: Any): Any {
        var scope = _scope
        while (true) {
            val dependencyResolverStrategy = scope[dependency]
            if (dependencyResolverStrategy != null) {
                return dependencyResolverStrategy(args);
            }

            scope = scope[Dependency("IoC.Scope.Parent")]!!(args) as Scope;
        }
    }
}
