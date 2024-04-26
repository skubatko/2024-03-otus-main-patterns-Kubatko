package ru.skubatko.dev.hw04

import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw02.move.Vector.Companion.ZERO
import ru.skubatko.dev.test.Bicycle
import ru.skubatko.dev.verifyNotCalled

@DisplayName("Команда поворота в движении")
@ExtendWith(MockKExtension::class)
class MoveRotateMacroCommandTest {
    @RelaxedMockK lateinit var bicycle: Bicycle

    lateinit var command: MoveRotateMacroCommand<Bicycle>

    @BeforeEach
    internal fun setUp() {
        command = MoveRotateMacroCommand(bicycle)
    }

    @DisplayName("должна ожидаемо поворачивать объект и ожидаемо менять его скорость")
    @Test
    fun `should expectedly rotate object with expected velocity change`() {
        // given
        every { bicycle.getVelocity() } returns Vector(5, 0)
        every { bicycle.getDirection() } returns 3
        every { bicycle.getDirectionsNumber() } returns 9
        every { bicycle.getAngularVelocity() } returns 1

        // when
        command.execute()

        // then
        verify { bicycle.setDirection(4) }
        verify { bicycle.setVelocity(Vector(-2, 4)) }
    }

    @DisplayName("не должна поворачивать и менять скорость неподвижного объекта")
    @Test
    fun `should not rotate and change speed of immovable object`() {
        // given
        every { bicycle.getVelocity() } returns ZERO

        // when
        assertThatThrownBy { command.execute() }.isInstanceOf(CommandException::class.java)

        // then
        verifyNotCalled { bicycle.setDirection(any()) }
        verifyNotCalled { bicycle.setVelocity(any()) }
    }
}
