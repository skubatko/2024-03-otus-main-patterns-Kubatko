@file:Suppress("IMPLICIT_CAST_TO_ANY")

package ru.skubatko.dev.hw06

import ru.skubatko.dev.hw02.move.Movable
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw04.domain.Spaceship
import ru.skubatko.dev.hw05.Dependency.Companion.CLEAR_CURRENT_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.CREATE_SCOPE
import ru.skubatko.dev.hw05.Dependency.Companion.REGISTER
import ru.skubatko.dev.hw05.Dependency.Companion.SET_CURRENT_SCOPE
import ru.skubatko.dev.hw05.InitCommand
import ru.skubatko.dev.hw05.IoC
import ru.skubatko.dev.hw05.Scope
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Сгенерированный адаптер")
class SpaceshipOperationsMovableAdapterTest {

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(CREATE_SCOPE)
        IoC.resolve<Command>(SET_CURRENT_SCOPE, iocScope).execute()
        IoC.resolve<Command>(REGISTER, adapterDependency, adapterDependencyResolverStrategy).execute()
        IoC.resolve<Command>(REGISTER, movablePositionGetDependency, movablePositionGetResolverStrategy).execute()
        IoC.resolve<Command>(REGISTER, movablePositionSetDependency, movablePositionSetResolverStrategy).execute()
        IoC.resolve<Command>(REGISTER, movableVelocityGetDependency, movableVelocityGetResolverStrategy).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(CLEAR_CURRENT_SCOPE).execute()
    }

    @DisplayName("должен возвращать ожидаемую позицию")
    @Test
    fun `should return expected position`() {
        // given
        val position = Vector(1, 1)
        val obj = Spaceship(position = position)
        val adapter = IoC.resolve<SpaceshipOperationsMovable>(adapterDependency, Movable::class, obj)

        // when
        val actual = adapter.getPosition()

        //then
        assertThat(actual).isEqualTo(position)
    }

    @DisplayName("должен устанавливать ожидаемую позицию")
    @Test
    fun `should set expected position`() {
        // given
        val obj = Spaceship()
        val adapter = IoC.resolve<SpaceshipOperationsMovable>(adapterDependency, Movable::class, obj)
        val position = Vector(1, 1)

        // when
        adapter.setPosition(position)

        //then
        assertThat(obj.position).isEqualTo(position)
    }

    @DisplayName("должен возвращать ожидаемую  скорость")
    @Test
    fun `should return expected velocity`() {
        // given
        val obj = Spaceship(
            velocity = 5,
            direction = 3,
            directionsNumber = 9,
        )
        val adapter = IoC.resolve<SpaceshipOperationsMovable>(adapterDependency, Movable::class, obj)

        // when
        val actual = adapter.getVelocity()

        //then
        assertThat(actual).isEqualTo(Vector(-2, 4))
    }
}
