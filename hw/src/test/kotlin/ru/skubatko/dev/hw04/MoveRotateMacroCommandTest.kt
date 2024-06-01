package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.commands.MoveRotateMacroCommand
import ru.skubatko.dev.hw04.domain.Spaceship
import ru.skubatko.dev.hw04.exceptions.CommandException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Команда поворота в движении")
class MoveRotateMacroCommandTest {

    @DisplayName("должна ожидаемо поворачивать объект и перемещать его в ожидаемую позицию")
    @Test
    fun `should expectedly rotate the object and move it to the expected position`() {
        // given
        val spaceship = Spaceship(
            position = Vector(2, 5),
            velocity = 5,
            direction = 3,
            directionsNumber = 9,
            angularVelocity = 3,
        )
        val command = MoveRotateMacroCommand(spaceship)

        // when
        command.execute()

        // then
        assertThat(spaceship.position).isEqualTo(Vector(0, 1))
        assertThat(spaceship.direction).isEqualTo(6)
    }

    @DisplayName("не должна поворачивать и менять скорость неподвижного объекта")
    @Test
    fun `should not rotate and change speed of immovable object`() {
        // given
        val spaceship = Spaceship()
        val command = MoveRotateMacroCommand(spaceship)

        // when
        assertThatThrownBy { command.execute() }.isInstanceOf(CommandException::class.java)

        // then
        assertThat(spaceship.position).isEqualTo(Vector.ZERO)
        assertThat(spaceship.direction).isEqualTo(0)
    }
}
