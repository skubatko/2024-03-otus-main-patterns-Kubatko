package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw04.commands.BurnFuelCommand
import ru.skubatko.dev.hw04.domain.Fueled
import ru.skubatko.dev.hw04.domain.Liter
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Команда сжигания топлива")
@ExtendWith(MockKExtension::class)
class BurnFuelCommandTest {
    @RelaxedMockK lateinit var fueled: Fueled

    @InjectMockKs lateinit var burnFuelCommand: BurnFuelCommand

    @DisplayName("должна уменьшать количество топлива при движении")
    @Test
    fun `should decrease fuel level when move`() {
        // given
        every { fueled.getVolume() } returns Liter(10)
        every { fueled.getConsumption() } returns Liter(3)

        // when
        burnFuelCommand.execute()

        // then
        verify { fueled.setVolume(Liter(7)) }
    }
}
