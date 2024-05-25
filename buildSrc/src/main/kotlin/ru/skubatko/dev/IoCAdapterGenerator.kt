package ru.skubatko.dev

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class IoCAdapterGenerator {

    fun generate(packageName: String, outputDir: File) {

        val className = "SpaceshipOperationsMovableAdapter"

        val kotlinFileSpecBuilder = FileSpec.builder(packageName, className)

        val classBuilder = TypeSpec.classBuilder(className)
        val property = PropertySpec.builder("message", String::class)
            .initializer(generateContent())
            .build()

        val clazz = classBuilder.addProperty(property).build()

        val kotlinFileSpec = kotlinFileSpecBuilder.addType(clazz).build()
        kotlinFileSpec.writeTo(outputDir)
    }

    private fun generateContent() =
        """
            package ru.skubatko.dev.hw06
            
            import ru.skubatko.dev.hw02.move.Vector
            import ru.skubatko.dev.hw04.domain.UObject
            import ru.skubatko.dev.hw05.Dependency
            import ru.skubatko.dev.hw05.IoC
            
            class SpaceshipOperationsMovableAdapter(
                private val obj: UObject
            ) : SpaceshipOperationsMovable {
            
                override fun getPosition(): Vector =
                    IoC.resolve(Dependency("Spaceship.Operations.Movable:position.get"), obj)
            
                override fun setPosition(newValue: Vector): Vector =
                    IoC.resolve(Dependency("Spaceship.Operations.Movable:position.set"), obj, newValue)
            
                override fun getVelocity(): Vector =
                    IoC.resolve(Dependency("Spaceship.Operations.Movable:velocity.get"), obj)
            }
        """.trimIndent()
}
