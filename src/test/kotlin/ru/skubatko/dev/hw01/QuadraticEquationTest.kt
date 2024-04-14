package ru.skubatko.dev.hw01

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.Double.NEGATIVE_INFINITY
import java.lang.Double.NaN
import java.lang.Double.POSITIVE_INFINITY
import java.util.stream.Stream

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

    @DisplayName("должно возвращать один корень кратности два")
    @Test
    fun `should return (x1= x2 = -1) for x^2+2x+1 = 0`() {
        // given
        val a = 0.9999998
        val b = 2.0000001
        val c = 0.999998

        // when
        val result = QuadraticEquation.solve(a, b, c)

        // then
        assertThat(result).containsOnly(-1.00000025000005)
    }

    @DisplayName("должно выбрасывать исключение.при a = 0")
    @Test
    fun `should throw exception when a = 0`() {
        // given
        val a = 0.0000000001
        val b = 1.0
        val c = 1.0

        // when + then
        assertThatThrownBy { QuadraticEquation.solve(a, b, c) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @DisplayName("должен выбрасывать исключение.при нечисловых аргументах")
    @ParameterizedTest(name = "{0} - {index}")
    @MethodSource("generateData")
    fun `should throw exception when a = 0`(name: String, a: Double, b: Double, c: Double) {
        assertThatThrownBy { QuadraticEquation.solve(a, b, c) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    companion object {
        @JvmStatic
        private fun generateData(): Stream<Arguments> =
            Stream.of(
                Arguments.of("Infinity(+)", POSITIVE_INFINITY, 1.0, 1.0),
                Arguments.of("Infinity(+)", 1.0, POSITIVE_INFINITY, 1.0),
                Arguments.of("Infinity(+)", 1.0, 1.0, POSITIVE_INFINITY),
                Arguments.of("Infinity(-)", NEGATIVE_INFINITY, 1.0, 1.0),
                Arguments.of("Infinity(-)", 1.0, NEGATIVE_INFINITY, 1.0),
                Arguments.of("Infinity(-)", 1.0, 1.0, NEGATIVE_INFINITY),
                Arguments.of("NaN", NaN, 1.0, 1.0),
                Arguments.of("NaN", 1.0, NaN, 1.0),
                Arguments.of("NaN", 1.0, 1.0, NaN),
            )
    }
}
