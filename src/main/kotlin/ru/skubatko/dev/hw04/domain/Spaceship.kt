@file:Suppress("unused")

package ru.skubatko.dev.hw04.domain

import ru.skubatko.dev.hw02.move.Vector
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class Spaceship(
    var position: Vector = Vector.ZERO,
    var velocity: Int = 0,
    var direction: Int = 0,
    private val directionsNumber: Int = 0,
    var angularVelocity: Int = 0,
    var volume: Liter = Liter.DEFAULT,
    private val consumption: Liter = Liter.DEFAULT,
) : UObject {

    override fun getProperty(key: String): Any? =
        this::class.declaredMemberProperties
            .first { it.name == key }
            .also { it.isAccessible = true }
            .getter.call(this)

    override fun setProperty(key: String, newValue: Any) {
        val property = this::class.declaredMemberProperties.find { it.name == key }
        property?.isAccessible = true
        if (property is KMutableProperty<*>) {
            property.setter.call(this, newValue)
        }
    }
}
