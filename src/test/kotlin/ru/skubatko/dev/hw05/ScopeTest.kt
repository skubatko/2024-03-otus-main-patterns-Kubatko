package ru.skubatko.dev.hw05

import ru.skubatko.dev.hw03.Command
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("IoC")
class ScopeTest {

    @BeforeEach
    fun setUp() {
        InitCommand().execute()
        val iocScope = IoC.resolve<Scope>(Dependency("IoC.Scope.Create"))
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Set"), iocScope).execute()
    }

    @AfterEach
    fun tearDown() {
        IoC.resolve<Command>(Dependency("IoC.Scope.Current.Clear")).execute()
    }

    @DisplayName("должен разрешать зарегистрированную зависимость в текущем контексте")
    @Test
    fun `should resolve registered dependency in current scope`() {
        IoC.resolve<Command>(Dependency("IoC.Register"), Dependency("someDependency"), { _: Any -> 1 }).execute()
        assertThat(IoC.resolve<Int>(Dependency("someDependency"))).isEqualTo(1)
    }
}
