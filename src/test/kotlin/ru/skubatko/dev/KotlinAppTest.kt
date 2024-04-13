package ru.skubatko.dev

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Основное приложение")
internal class KotlinAppTest {

    @DisplayName("должно возвращать ожидаемую сумму")
    @Test
    fun `should return expected sum`() {
        // given
        val app = KotlinApp()
        val a = 2
        val b = 3
        val expected = 5

        // when
        val actual = app.sum(a, b)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
