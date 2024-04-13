package ru.skubatko.dev.hw01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Квадратное уравнение")
class QuadraticEquationTest {

    @DisplayName("должно возвращать пустой массив (корней нет) для уравнения x^2+1 = 0")
    @Test
    fun `should return empty for x^2+1 = 0`() {
        // given
        val a = 1.0
        val b = 0.0
        val c = 1.0

        // when
        val result = QuadraticEquation.solve(a, b, c)

        // then
        assertThat(result).isEmpty()
    }

    @DisplayName("должно возвращать два корня (x1=1, x2=-1) для уравнения x^2-1 = 0")
    @Test
    fun `should return (x1=1, x2=-1) for x^2-1 = 0`() {
        // given
        val a = 1.0
        val b = 0.0
        val c = -1.0

        // when
        val result = QuadraticEquation.solve(a, b, c)

        // then
        assertThat(result).containsOnly(1.0, -1.0)
    }

    @DisplayName("должно возвращать один корень (x1= x2 = -1) для уравнения x^2+2x+1 = 0")
    @Test
    fun `should return (x1= x2 = -1) for x^2+2x+1 = 0`() {
        // given
        val a = 1.0
        val b = 2.0
        val c = 1.0

        // when
        val result = QuadraticEquation.solve(a, b, c)

        // then
        assertThat(result).containsOnly(-1.0, -1.0)
    }
}
