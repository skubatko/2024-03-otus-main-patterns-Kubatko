package ru.skubatko.dev.hw06

import ru.skubatko.dev.hw02.move.Vector
import ru.skubatko.dev.hw04.domain.Spaceship
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Сгенерированный адаптер")
class SpaceshipOperationsMovableAdapterTest {

    @DisplayName("должен возвращать ожидаемую позицию")
    @Test
    fun `should return expected position`() {
        // given
        val position = Vector(1, 1)
        val obj = Spaceship()
        val adapter = SpaceshipOperationsMovableAdapter(obj)

        // when
        val actual = adapter.getPosition()

        //then
        assertThat(actual).isEqualTo(position)
    }
}
