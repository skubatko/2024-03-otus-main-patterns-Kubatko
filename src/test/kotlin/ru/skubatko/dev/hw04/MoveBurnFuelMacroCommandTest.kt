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
import ru.skubatko.dev.test.Car
import ru.skubatko.dev.verifyNotCalled

@DisplayName("Команда движения по прямой с расходом топлива")
@ExtendWith(MockKExtension::class)
class MoveBurnFuelMacroCommandTest {
    @RelaxedMockK lateinit var car: Car

    lateinit var command: MoveBurnFuelMacroCommand<Car>

    @BeforeEach
    internal fun setUp() {
        command = MoveBurnFuelMacroCommand(car)
    }

    @DisplayName("должна ожидаемо перемещать объект с ожидаемым расходом топлива")
    @Test
    fun `should expectedly move object with expected fuel consumption`() {
        // given
        every { car.getVolume() } returns Liter(10)
        every { car.getConsumption() } returns Liter(3)
        every { car.getPosition() } returns Vector(0, 0)
        every { car.getVelocity() } returns Vector(1, 0)

        // when
        command.execute()

        // then
        verify { car.setPosition(Vector(1, 0)) }
        verify { car.setVolume(Liter(7)) }
    }

    @DisplayName("не должна перемещать объект когда недостаточно топлива")
    @Test
    fun `should not move object when fuel level is not enough`() {
        // given
        every { car.getVolume() } returns Liter(1)
        every { car.getConsumption() } returns Liter(3)

        // when
        assertThatThrownBy { command.execute() }.isInstanceOf(CommandException::class.java)

        // then
        verifyNotCalled { car.setPosition(any()) }
        verifyNotCalled { car.setVolume(any()) }
    }
}
