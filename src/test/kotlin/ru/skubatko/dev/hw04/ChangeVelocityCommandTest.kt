package ru.skubatko.dev.hw04

import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.commands.ChangeVelocityCommand
import ru.skubatko.dev.hw04.domain.Spaceship
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Команда модификации вектора мгновенной скорости при повороте")
class ChangeVelocityCommandTest {

    @DisplayName("должна перемещать объект в ожидаемую позицию")
    @Test
    fun `should move the object to the expected position`() {
        // given
        val spaceship = Spaceship(
            position = Vector(1, 3),
            velocity = 5,
            direction = 4,
            directionsNumber = 9,
            angularVelocity = 4,
        )
        val command = ChangeVelocityCommand(spaceship)

        // when
        command.execute()

        // then
        assertThat(spaceship.position).isEqualTo(Vector(-3, 4))
    }
}
