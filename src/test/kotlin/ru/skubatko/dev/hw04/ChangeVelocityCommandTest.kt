package ru.skubatko.dev.hw04

import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.test.Bicycle

@DisplayName("Команда модификации вектора мгновенной скорости при повороте")
@ExtendWith(MockKExtension::class)
class ChangeVelocityCommandTest {
    @RelaxedMockK lateinit var bicycle: Bicycle

    lateinit var command: ChangeVelocityCommand<Bicycle>

    @BeforeEach
    internal fun setUp() {
        command = ChangeVelocityCommand(bicycle)
    }

    @DisplayName("должна ожидаемо устанавливать скорость при повороте")
    @Test
    fun `should expectedly set velocity when rotated`() {
        // given
        every { bicycle.getDirection() } returns 3
        every { bicycle.getDirectionsNumber() } returns 9
        every { bicycle.getVelocity() } returns Vector(5, 0)

        // when
        command.execute()

        // then
        verify { bicycle.setVelocity(Vector(-2, 4)) }
    }
}
