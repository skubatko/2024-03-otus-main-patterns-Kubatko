package ru.skubatko.dev.hw02.rotate

import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Команда поворота")
@ExtendWith(MockKExtension::class)
class RotateCommandTest {
    @MockK
    lateinit var rotatable: Rotatable

    @DisplayName("должна ожидаемо выполнять поворот объекта ")
    @Test
    fun `should expectedly execute object moving in line`() {
        // given
        every { rotatable.getDirection() } returns 1
        every { rotatable.getAngularVelocity() } returns 4
        every { rotatable.getDirectionsNumber() } returns 3
        every { rotatable.setDirection(any()) } just Runs

        val sut = RotateCommand(rotatable)

        // when
        sut.execute()

        // then
        verify { rotatable.setDirection(2) }
    }

    @DisplayName("должна выбрасывать исключение при попытке повернуть объект, у которого невозможно прочитать направление")
    @Test
    fun `should throw exception when try to rotate object with undefined direction`() {
        // given
        every { rotatable.getDirection() } throws RuntimeException()
        every { rotatable.getAngularVelocity() } returns 4
        every { rotatable.getDirectionsNumber() } returns 3
        every { rotatable.setDirection(any()) } just Runs

        val sut = RotateCommand(rotatable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("должна выбрасывать исключение при попытке повернуть объект, у которого невозможно прочитать угловую скорость")
    @Test
    fun `should throw exception when try to rotate object with undefined angular velocity`() {
        // given
        every { rotatable.getDirection() } returns 1
        every { rotatable.getAngularVelocity() } throws RuntimeException()
        every { rotatable.getDirectionsNumber() } returns 3
        every { rotatable.setDirection(any()) } just Runs

        val sut = RotateCommand(rotatable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("должна выбрасывать исключение при попытке повернуть объект, у которого невозможно прочитать число направлений")
    @Test
    fun `should throw exception when try to rotate object with undefined directions number`() {
        // given
        every { rotatable.getDirection() } returns 1
        every { rotatable.getAngularVelocity() } returns 4
        every { rotatable.getDirectionsNumber() } throws RuntimeException()
        every { rotatable.setDirection(any()) } just Runs

        val sut = RotateCommand(rotatable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("должна выбрасывать исключение при попытке повернуть объект, который невозможно повернуть")
    @Test
    fun `should throw exception when try to rotate non rotatable object`() {
        // given
        every { rotatable.getDirection() } returns 1
        every { rotatable.getAngularVelocity() } returns 4
        every { rotatable.getDirectionsNumber() } returns 3
        every { rotatable.setDirection(any()) } throws RuntimeException()

        val sut = RotateCommand(rotatable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }
}
