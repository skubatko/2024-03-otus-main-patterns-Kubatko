package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw04.commands.CheckFuelCommand
import ru.skubatko.dev.hw04.domain.Fueled
import ru.skubatko.dev.hw04.domain.Liter
import ru.skubatko.dev.hw04.exceptions.CommandException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Команда проверки наличия топлива")
@ExtendWith(MockKExtension::class)
class CheckFuelCommandTest {
    @MockK lateinit var fueled: Fueled

    @InjectMockKs lateinit var checkFuelCommand: CheckFuelCommand

    @DisplayName("должна проверять, что топлива достаточно")
    @Test
    fun `should check if fuel level is enough`() {
        // given
        every { fueled.getVolume() } returns Liter(10)
        every { fueled.getConsumption() } returns Liter(5)

        // when + then
        assertThatCode { checkFuelCommand.execute() }.doesNotThrowAnyException()
    }

    @DisplayName("должна проверять, что топлива недостаточно")
    @Test
    fun `should check if fuel level is exceeded`() {
        // given
        every { fueled.getVolume() } returns Liter(0)
        every { fueled.getConsumption() } returns Liter(5)

        // when + then
        assertThatThrownBy { checkFuelCommand.execute() }.isInstanceOf(CommandException::class.java)
    }
}
