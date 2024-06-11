package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.commands.MoveBurnFuelMacroCommand
import ru.skubatko.dev.hw04.domain.Liter
import ru.skubatko.dev.hw04.domain.Spaceship
import ru.skubatko.dev.hw04.exceptions.CommandException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Команда движения по прямой с расходом топлива")
class MoveBurnFuelMacroCommandTest {

    @DisplayName("должна ожидаемо перемещать объект с ожидаемым расходом топлива")
    @Test
    fun `should expectedly move object with expected fuel consumption`() {
        // given
        val spaceship = Spaceship(
            position = Vector(1, 3),
            velocity = 5,
            direction = 1,
            directionsNumber = 4,
            volume = Liter(10),
            consumption = Liter(3)
        )
        val command = MoveBurnFuelMacroCommand(spaceship)

        // when
        command.execute()

        // then
        assertThat(spaceship.position).isEqualTo(Vector(1, 8))
        assertThat(spaceship.volume).isEqualTo(Liter(7))
    }

    @DisplayName("не должна перемещать объект когда недостаточно топлива")
    @Test
    fun `should not move object when fuel level is not enough`() {
        // given
        val spaceship = Spaceship(
            position = Vector(7, 11),
            velocity = 12,
            direction = 1,
            directionsNumber = 4,
            volume = Liter(2),
            consumption = Liter(3)
        )
        val command = MoveBurnFuelMacroCommand(spaceship)

        // when
        assertThatThrownBy { command.execute() }.isInstanceOf(CommandException::class.java)

        // then
        assertThat(spaceship.position).isEqualTo(Vector(7, 11))
    }
}
