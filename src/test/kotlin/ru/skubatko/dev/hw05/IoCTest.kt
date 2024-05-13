package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import ru.skubatko.dev.hw05.Dependency.Companion.STRATEGY_UPDATE
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("IoC")
class IoCTest {

    @DisplayName("должен обновлять стратегию разрешения зависимости")
    @Test
    fun `should update resolve dependency strategy`() {
        var wasCalled = false

        IoC.resolve<Command>(
            STRATEGY_UPDATE,
            { args: IoCStrategy ->
                wasCalled = true
                args
            }
        ).execute()

        assertThat(wasCalled).isTrue()
    }

    @DisplayName("должен выбрасывать IllegalArgumentException если зависимость не найдена")
    @Test
    fun `should throw IllegalArgumentException if dependency is not found`() {
        assertThatThrownBy {
            IoC.resolve<Any>(Dependency("UnknownDependency"))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @DisplayName("должен выбрасывать ClassCastException если зависимость разрешает другой тип")
    @Test
    fun `should throw ClassCastException if dependency resolves another type`() {
        assertThatThrownBy {
            IoC.resolve<String>(STRATEGY_UPDATE, { args: IoCStrategy -> args }).length
        }.isInstanceOf(ClassCastException::class.java)
    }
}
