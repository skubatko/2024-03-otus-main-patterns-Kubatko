package ru.skubatko.dev.hw02

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

@DisplayName("Команда движения")
@ExtendWith(MockKExtension::class)
class MoveTest {
    @MockK
    lateinit var movable: Movable

    @DisplayName("должна ожидаемо выполнять перемещение объекта по прямой")
    @Test
    fun `should expectedly execute object moving in line`() {
        // given
        every { movable.getPosition() } returns Vector(12, 5)
        every { movable.getVelocity() } returns Vector(-5, 3)
        every { movable.setPosition(any()) } just Runs

        val sut = Move(movable)

        // when
        sut.execute()

        // then
        verify { movable.setPosition(Vector(7, 8)) }
    }

    @DisplayName("должна выбрасывать исключение при попытке сдвинуть объект, у которого невозможно прочитать положение в пространстве")
    @Test
    fun `should throw exception when try to move object with undefined position`() {
        // given
        every { movable.getPosition() } throws RuntimeException()
        every { movable.getVelocity() } returns Vector(-5, 3)
        every { movable.setPosition(any()) } just Runs

        val sut = Move(movable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("должна выбрасывать исключение при попытке сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости")
    @Test
    fun `should throw exception when try to move object with undefined velocity`() {
        // given
        every { movable.getPosition() } returns Vector(12, 5)
        every { movable.getVelocity() } throws RuntimeException()
        every { movable.setPosition(any()) } just Runs

        val sut = Move(movable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("должна выбрасывать исключение при попытке сдвинуть объект, у которого невозможно изменить положение в пространстве")
    @Test
    fun `should throw exception when try to move unmovable object`() {
        // given
        every { movable.getPosition() } returns Vector(12, 5)
        every { movable.getVelocity() } returns Vector(-5, 3)
        every { movable.setPosition(any()) } throws RuntimeException()

        val sut = Move(movable)

        // when + then
        assertThatThrownBy { sut.execute() }.isInstanceOf(RuntimeException::class.java)
    }
}
