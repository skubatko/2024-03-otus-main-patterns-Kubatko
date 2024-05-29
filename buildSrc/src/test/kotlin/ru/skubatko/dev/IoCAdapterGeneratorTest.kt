package ru.skubatko.dev

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets

@DisplayName("Генератор адаптеров")
class IoCAdapterGeneratorTest {
    private val generator = IoCAdapterGenerator()

    @DisplayName("должен генерировать ожидаемый адаптер")
    @Test
    fun `should generate expected adapter`() {
        generator.generate("ru.skubatko.dev", File("build/generated"))

        val file = File("build/generated/src/main/kotlin/ru/skubatko/dev", "SpaceshipOperationsMovableAdapter.kt")
        assertThat(file)
            .exists()
            .content(StandardCharsets.UTF_8)
            .contains(
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
    
                    override fun finish() {
                        IoC.resolve<Void>(Dependency("Spaceship.Operations.Movable:finish"), obj)
                    }
                }
            """.trimIndent()
            )
        file.delete()
    }
}
