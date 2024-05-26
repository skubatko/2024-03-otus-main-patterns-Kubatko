package ru.skubatko.dev

import java.io.File

class IoCAdapterGenerator {

    fun generate(packageName: String, outputDir: File) {
        val dir = "$outputDir/src/main/kotlin/${packageName.replace('.', '/')}"
        with(File(dir, "SpaceshipOperationsMovableAdapter.kt")) {
            parentFile.mkdirs()
            writeText(generateContent())
        }

//        val className = "SpaceshipOperationsMovableAdapter"
//        val kotlinFileSpecBuilder = FileSpec.builder(packageName, className)
//        val classBuilder = TypeSpec.classBuilder(className)
//        val clazz = classBuilder.build()
//        val kotlinFileSpec = kotlinFileSpecBuilder.addType(clazz).build()
//        kotlinFileSpec.writeTo(outputDir)
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
