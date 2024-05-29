package ru.skubatko.dev.hw06

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.domain.UObject
import ru.skubatko.dev.hw05.Args
import ru.skubatko.dev.hw05.Dependency
import kotlin.math.cos
import kotlin.math.sin

val adapterDependency = Dependency("Adapter")
val adapterDependencyResolverStrategy =
    { args: Args ->
        when (args[0]) {
            Movable::class -> SpaceshipOperationsMovableAdapter(args[1] as UObject)
            else -> null
        }
    }

val movablePositionGetDependency = Dependency("Spaceship.Operations.Movable:position.get")
val movablePositionGetResolverStrategy =
    { args: Args -> (args[0] as UObject).getProperty("position") as Vector }

val movablePositionSetDependency = Dependency("Spaceship.Operations.Movable:position.set")
val movablePositionSetResolverStrategy = { args: Args ->
    val oldValue = (args[0] as UObject).getProperty("position") as Vector
    (args[0] as UObject).setProperty("position", args[1] as Vector)
    oldValue
}

val movableVelocityGetDependency = Dependency("Spaceship.Operations.Movable:velocity.get")
val movableVelocityGetResolverStrategy = { args: Args ->
    val obj = (args[0] as UObject)
    val d = obj.getProperty("direction") as Int
    val n = obj.getProperty("directionsNumber") as Int
    val v = obj.getProperty("velocity") as Int
    val dRad = 2 * Math.PI * d / n
    Vector((v * cos(dRad)).toInt(), (v * sin(dRad)).toInt())
}
