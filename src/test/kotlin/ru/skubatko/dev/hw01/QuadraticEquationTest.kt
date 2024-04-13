package ru.skubatko.dev.hw01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Квадратное уравнение")
class QuadraticEquationTest {

    @DisplayName("должно возвращать пустой массив (корней нет) для уравнения x^2+1 = 0")
    @Test
    fun `should return empty result for x^2+1 = 0`() {
        // given
        val a = 1.0
        val b = 0.0
        val c = 1.0

        // when
        val result = QuadraticEquation.solve(a, b, c)

        // then
        assertThat(result).isEmpty()
    }
}
